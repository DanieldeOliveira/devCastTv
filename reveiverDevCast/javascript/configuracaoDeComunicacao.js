var castReceiverManager;
var appConfig;
var messageBusSenderToChromecast;
var messageBusSenderToSender;
var messageBusChromecastSenderOwner;
var senderDono;
var controleAcesso = false;
var msgPermissao;
var dimensoesTela;
var mediaElement;
var mediaManager;
window.onload = function(){


    /*
     Instância de CastReceiverManager.
     CastReceiverManager é um singleton responsável pelo gerenciamento do receiver, permitindo a comunicação entre
     os dispositivos,
     */
    /*window.mediaElement = document.getElementById('vdo');
    window.mediaManager = new cast.receiver.MediaManager(window.mediaElement);*/
    mediaElement = document.getElementById('vdo');
    mediaManager = new cast.receiver.MediaManager(mediaElement);
    castReceiverManager = cast.receiver.CastReceiverManager.getInstance();


    /*
     ********************** Parte de Configuração dos paramêtros do receiver do aplicativo **********************
     */


    /*
     Configuração  do modo  Debug.
     */
    cast.player.api.setLoggerLevel(cast.player.api.LoggerLevel.DEBUG);
    cast.receiver.logger.setLevelValue(cast.receiver.LoggerLevel.DEBUG);


    /*
     A classe Config permite configurar paramêtros da aplicação, como maxInactivity ( tempo máximo de inatividade
     da aplicação, nesse caso foi definido undefined para que o usuário só se desconecte-se se ele quiser e não auto-
     maticamente pelo receiver) e statusText que representa o status da aplicação.
     */
    appConfig = new cast.receiver.CastReceiverManager.Config();
    appConfig.maxInactivity = undefined;
   // appConfig.maxInactivity = 1000000000000000000;




    /*
     Configuração do namespace de um canal.
     O canal é um meio de comunicação entre o sender e o receiver, e pode-se definir mais de um canal para uma
     mesma aplicação.
     O nome do canal deve seguir o padraao urn:x-cast:nomequevcescolher
     */

    messageBusSenderToChromecast = castReceiverManager.getCastMessageBus('urn:x-cast:DevCastSenderToChromecast');




    /*Parte que pega as dimensões da tela da TV. Essas dimensões será enviadas ao sender assim que seu acesso for permi-
      tido pelo sender dono.
     */




    dimensoesTela = "{tipo = dimensaoTela,width = " + window.screen.width + ", height = "+ window.screen.height +" }";


    /*
     ********************** Parte de configuração dos eventos de comunicação entre sender e receiver ******************
     */

    /* Método quando o receiver estiver pronto para que o usuário interaja. */
    castReceiverManager.onReady = function(event) {
        console.log('Receiver pronto: ' + JSON.stringify(event.data));
        castReceiverManager.setApplicationState("Receiver pronto!");


    };

    /*
     Método que captura o evento de quando o sender se conecta a um chromecast
     */
    castReceiverManager.onSenderConnected = function(event) {

        messageBusSenderToChromecast.send(event.senderId,dimensoesTela);


        if(castReceiverManager.getSenders().length == 1){
            //senderDono = event.senderId;
        }
        else {
            if(controleAcesso == true){

                //msgPermissao = '{tipo:solicitarPermissao,senderId : '+ event.senderId + '}';
                //messageBusChromecastSenderOwner.send(senderDono,msgPermissao);
            }
        }
        console.log(window.castReceiverManager.getSender(event.data).userAgent);
    };

    /*
     Método que captura o evendo de quando o sender se desconecta do chromecast.
     Caso esse serder for o único ou o último sender conectado a aplicação termina após essa desconecção
     */
    castReceiverManager.onSenderDisconnected = function(event) {
        console.log('Sender desconectado: ' + event.data);
        if (castReceiverManager.getSenders().length == 0) {
            castReceiverManager.stop();
        }
    };

    /*
     Método que captura a mudança de volume feita pelo sender
     */
    castReceiverManager.onSystemVolumeChanged = function(event) {
        console.log('Received System Volume Changed event: ' + event.data['level'] + ' ' +
            event.data['muted']);
    };

    messageBusSenderToChromecast.onMessage = function(event) {

        console.log("Mensagem recebida do sender = " + event.senderId);
        console.log("Conteudo da mensagem = " + event.data);

        var msgSender = JSON.parse(event.data);

            if(msgSender.tipo == 'addElemento'){
                console.log("Caiu no add elemento");

                //inserirElemento(msgSender.elemento);
                //adicionarImagem(msgSender.elemento);
                //testeFuncao(msgSender.elemento);
                inserirElemento(msgSender.elemento);
            }
            else if (msgSender.tipo == 'editElemento'){
                console.log("Caiu no editElemento");

            }
            else if (msgSender.tipo == 'criarAplicacao'){

                console.log("Caiu no criarAplicacao");
                esconderElemento('telaInicial');
                mostrarCriarAplicacao();

            }
            else if (msgSender.tipo == 'removeMsg'){
                esconderElemento('h1texto');
            }
            else {
                    console.log("Caiu no else");
            }
    }



    castReceiverManager.start();

};
