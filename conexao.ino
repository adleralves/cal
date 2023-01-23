/* Feito por: Bruno da Silva Almeida */

/*#############################################*/
               /* Includes */
/*#############################################*/

#include <WiFi.h>
#include <Keypad.h>
#include <HTTPClient.h>
WiFiClient client;

/*#############################################*/
         /* Mapeamento do hardware */
/*#############################################*/

char teclas[4][4] = {{'1','2','3','A'},
                     {'4','5','6','B'},
                     {'7','8','9','C'},
                     {'*','0','#','D'}};

byte pinosLinhas[]  = {13,12,14,27};
byte pinosColunas[] = {26,25,33,32};

Keypad teclado1 = Keypad( makeKeymap(teclas), pinosLinhas, pinosColunas, 4, 4);
char tecla_pressionada = teclado1.getKey();

/*#############################################*/
      /* Interligação servidor - Conexão */
/*#############################################*/

const char* ssid = "Lívia - 4G"; // outras oportunidades: "aula-mobile"/"Bruno"/"Secreta"/"Lívia - 4G"
const char* password = "Jaker32@"; // outras oportunidades: "aula-mobile"/"12345678bb"/"$3cr3t@69"/"Jaker32@"
const char* host =  "192.168.15.5"; 

const int httpPort = 80;
String url = "";

/*#############################################*/
            /* Condição da porta */
/*#############################################*/

// tecla D para iniciar a colocação de senha3
// tecla A para confirmar

int result;
String login_acesso = ""; // Comparador de acesso (login)
String senha_acesso = ""; // Comparador de acesso (senha)
String condicao_3 = ""; // Comparador do OK

String sala = "64"; // Sensor da sala
int iArmazena_1; // Status da porta (ABERTA ou FECHADA), sensor fim de curso
int sensor = 0; // Sensor de Status da porta

int iContador_tempo = 0;

void setup(){

  /*#############################################*/
                      /* Motor */
  /*#############################################*/
    
  pinMode(2, OUTPUT); // Controle do motor (FECHAMENTO)
  pinMode(4, OUTPUT); // Controle do motor (ABERTURA)
  
  /*#############################################*/
                      /* Sensor */
  /*#############################################*/
      
  pinMode (23,INPUT_PULLUP); // FECHADA
  pinMode (22,INPUT_PULLUP); // ABERTO


  pinMode(5, OUTPUT); // Controle do buzzer
  pinMode(18, OUTPUT); // led 1
  pinMode(19, OUTPUT); // led 2
  
  delay (10);
  Serial.begin(115200);
  
  /*#############################################*/
      /* Realizando a conexão com o servidor */
  /*#############################################*/
  
  conectando(ssid,password);
    
} // Fim do void setup.

void loop(){

  digitalWrite (18, LOW);
  digitalWrite (19, LOW);

  url = "http://" + String(host) + "/nodemcu/buscar.php"; // Buscar o acesso no banco de dados.

  // se alguma tecla foi pressionada
  tecla_pressionada = teclado1.getKey();

  if (tecla_pressionada != false){

    if (tecla_pressionada == '*'){

      digitalWrite (18,HIGH);
      
      sala = "";
  
      Serial.println();
      Serial.print("Iniciando troca de sala:");
      Serial.println();
  
      while (condicao_3 != "A"){

        
            
        tecla_pressionada = teclado1.getKey();
    
        if (tecla_pressionada != false){
    
          condicao_3 = tecla_pressionada;
    
          delay (100);
    
          // Para quando precionado, será adicionado um caracter
          if (tecla_pressionada != 'A' && tecla_pressionada != 'C'){    
  
            sala = sala + tecla_pressionada;
              
            Serial.print("Sala: ");
            Serial.println(sala);
                      
          } // (tecla_pressionada != 'A' && tecla_pressionada != 'C')
                  
          //Para quando precionado, será apagado
          if (tecla_pressionada != false && tecla_pressionada != 'A' && tecla_pressionada == 'C'){
                
            sala = "";
      
            Serial.print("Sala: ");
            Serial.println(sala);
                    
          } // Fim do if (tecla_pressionada != false && tecla_pressionada != 'A' && tecla_pressionada == 'C')
            
        } // Fim do if (tecla_pressionada != false)
            
      } // Fim do while (condicao_3 != "A")
          
      condicao_3 = "";

      digitalWrite (18,LOW);
      digitalWrite (5,HIGH);
      delay(1000);
      digitalWrite (5,LOW);
      delay(1000);
  
    } // Fim do if (tecla_pressionada == '*')

    result = procurando_acesso (tecla_pressionada,login_acesso,senha_acesso,condicao_3,sala);

  } // Fim do if (teclado_pressionado != false)

//  if (tecla_pressionada != false && tecla_pressionada)

  if (result == 1){

    if (!client.connect(host, httpPort)) {
  
      Serial.println("Falha na conexão");
      return;
  
    } // Fim do if !client.connect(host, httpPort)

    iArmazena_1 = digitalRead(22);

    while (iArmazena_1 != 0){
  
      digitalWrite (2, HIGH);
      delay (2000);
      digitalWrite (2,LOW);
      delay(1000);

      iArmazena_1 = digitalRead(22);
    
      sensor = 1;
    
      //iArmazena_1 = 0; //digitalRead(22);

    } // Fim do while (iArmazena_1 != 0)
    
    // We now create a URI for the request
    url = "/nodemcu/salvar.php?"; // Salvar no banco
    
    url += "estado=";
    url += sensor;
    url += "&nome=";
    url += sala;
    
    Serial.print("Requisitando URL: ");
    Serial.println(url);

    client.print(String("GET ") + url + " HTTP/1.1\r\n" + "Host: " + host + "\r\n" + "Connection: close\r\n\r\n");

    tecla_pressionada = teclado1.getKey();

    while (tecla_pressionada != 'D'){
      
      tecla_pressionada = teclado1.getKey();

      if (tecla_pressionada == 'D'){

        iArmazena_1 = digitalRead(23); 
        
        while (iArmazena_1 != 0){ 

          digitalWrite (4, HIGH);
          delay (2000);
          digitalWrite (4,LOW);
          delay(1000);

          sensor = 0;
  
          iArmazena_1 = digitalRead(23);

        } // Fim do while (iArmazena_1 != 1)

      } // Fim do if (tecla_pressionada == 'D')

    } // Fim do while (tecla_pressionada != 'D')

    // We now create a URI for the request

    if (!client.connect(host, httpPort)) {
  
      Serial.println("Falha na conexão");
      return;
  
    } // Fim do if !client.connect(host, httpPort)
    
    url = "/nodemcu/salvar.php?"; // Salvar no banco
    
    url += "estado=";
    url += sensor;
    url += "&nome=";
    url += sala;

    Serial.print("Requisitando URL: ");
    Serial.println(url);

    client.print(String("GET ") + url + " HTTP/1.1\r\n" + "Host: " + host + "\r\n" + "Connection: close\r\n\r\n");

    result = 0;
    
  } // Fim do if (result == 1)
  
  login_acesso = ""; // Comparador de acesso (login)
  senha_acesso = ""; // Comparador de acesso (senha)
  condicao_3 = ""; // Comparador do OK
  sensor = 0; // Sensor de Status da porta
    
} // Fim do void loop

// #################################
          // Métodos
// #################################

int procurando_acesso (char tecla_pressionada,String login_acesso,String senha_acesso,String condicao_3,String sala){

  int result;

  if (tecla_pressionada == 'D'){

    digitalWrite (19,HIGH);

    Serial.println();
    Serial.print("Iniciando o login");
    Serial.println();

    while (condicao_3 != "A"){
        
      tecla_pressionada = teclado1.getKey();

      if (tecla_pressionada != false){

        condicao_3 = tecla_pressionada;

        delay (100);

        // Para quando precionado, será adicionado um caracter
        if (tecla_pressionada != 'A' && tecla_pressionada != 'C'){
    
          login_acesso = login_acesso + tecla_pressionada;
      
          Serial.print("Login: ");
          Serial.println(login_acesso);
              
        } // Fim do if (tecla_pressionada != false && tecla_pressionada != 'A' && tecla_pressionada != 'C')
          
        //Para quando precionado, será apagado
        if (tecla_pressionada != false && tecla_pressionada != 'A' && tecla_pressionada == 'C'){
        
          login_acesso = "";
    
          Serial.print("Login: ");
          Serial.println(login_acesso);
            
        } // Fim do if (tecla_pressionada != false && tecla_pressionada != 'A' && tecla_pressionada == 'C')

      } // Fim do if (tecla_pressionada != false)
 
    } // Fim do while (condicao_3 != "A")

    digitalWrite (5,HIGH);
    delay(1000);
    digitalWrite (5,LOW);
    delay(1000);

    condicao_3 = "";

    Serial.println();
    Serial.print("Iniciando a senha");
    Serial.println();
            
    while (condicao_3 != "A"){

      tecla_pressionada = teclado1.getKey();

      condicao_3 = tecla_pressionada;
      
      delay(100);

      if (tecla_pressionada != false && tecla_pressionada != 'A' && tecla_pressionada != 'C'){

        senha_acesso = senha_acesso + tecla_pressionada;

        Serial.print("Senha: ");
        Serial.println(senha_acesso);

      } // Fim do if (tecla_pressionada != 'A' && tecla_pressionada != 'C')

      /*Para quando precionado, será apagado*/
      if (tecla_pressionada != false && tecla_pressionada != 'A' && tecla_pressionada == 'C'){
      
        senha_acesso = "";

        Serial.print("Senha: ");
        Serial.println();
          
      } // Fim do if (tecla_pressionada != false && tecla_pressionada != 'A' && tecla_pressionada == 'C')

      digitalWrite (18,LOW);
      
    } // Fim do while condicao != senha

    digitalWrite (5,HIGH);
    delay(1000);
    digitalWrite (5,LOW);
    delay(1000);

} // Fim do if (tecla_pressionada == 'D')

  if (login_acesso.length() != 0){

    if (solicitaAcesso(login_acesso, senha_acesso,sala)){
    
      Serial.println("Acesso autorizado!");

      result = 1;
    
    } // Fim do if (login_acesso, senha_acesso,sala))
  
    else{
    
      Serial.println("Acesso negado!");

      while (iContador_tempo <= 3){

        digitalWrite(5,HIGH);
        delay(500);
        digitalWrite(5,LOW);
        delay(500);

        iContador_tempo = iContador_tempo + 1;
        delay (1000);

        
      }

      iContador_tempo = 0;

      result = 2;
    
    } // Fim do else 
    
  } // Fim do if (login_acesso != null and)

  return result;

} // Fim procurando acesso.

// #######################################
         // Acionamento motor
// #######################################

String acionamento_motor_abertura (){

  int iArmazena_1; // Status da porta (ABERTA ou FECHADA), sensor fim de curso
  String sensor = ""; // Sensor de Status da 

  iArmazena_1 = digitalRead(22);

  while (iArmazena_1 != 0){
  
    digitalWrite (4, HIGH);
    delay (2000);
    digitalWrite (4,LOW);
    delay(1000);
  
    sensor = "Aberto";
  
    iArmazena_1 = 0; //digitalRead(22);

  } // Fim do while (iArmazena_1 != 0)

  return sensor;
  
} // Fim do acionamento motor abertura

// #######################################
      // Envia requisição ao server
// #######################################

bool solicitaAcesso(String login, String senha, String sala){

    bool flagAutorizado = false;

    HTTPClient http;
    http.begin(client, url);
    http.addHeader("Content-Type", "application/json");
    // Data to send with HTTP POST
    String httpRequestData = "{\"login\": \"" + login + "\", \"senha\": \""+senha+"\", \"sala\": \""+sala+"\"}";          
    int httpResponseCode = http.POST(httpRequestData);
 
    Serial.print("HTTP Response code: ");
    Serial.println(httpResponseCode);
    
    // Free resources
    http.end();
  
    return (httpResponseCode == 200);  
    
} // Fim do solicitaAcesso

// ############################################
        // Inicializa conexao com WiFi
// ############################################

void conectando (const char* ssid, const char* password){
 
  Serial.print("CONECTANDO COM ");
  Serial.println(ssid);
  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
      
    delay(500);
        
    Serial.print(".");
        
  } // Fim do while

  Serial.println("");
  Serial.println("WiFi CONECTADO");
  Serial.println("ENDEREÇO DE IP: ");
  Serial.println(WiFi.localIP());
      
} // Fim do void conectando
