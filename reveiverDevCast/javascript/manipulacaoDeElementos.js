var idImagens = 00;
function inserirElemento(elemento){


    var elementoAInserir;
    if(elemento.tipoMidia == 'video'){

        elementoAInserir = document.createElement("video");
        elementoAInserir.src = dadosElementoAInserir.src;
        dadosElementoAInserir.id = "videofundo";
        $('.interacao').html(elementoAInserir);



    }
    else if(elemento.tipoMidia == 'img'){

        console.log("Inserindo a imagem");
        elementoAInserir = document.createElement("img");
        elementoAInserir.id = elemento.idImg;
        elementoAInserir.src = elemento.url;
        elementoAInserir.style.display = 'block';
        elementoAInserir.style.position = 'absolute';
        elementoAInserir.style.width = elemento.widthImagem +'px';
        elementoAInserir.style.height = elemento.heightImagem +'px';
        elementoAInserir.style.left = elemento.posicao.x+'px';
        elementoAInserir.style.top = elemento.posicao.y+'px';
        $('.imagens').html(elementoAInserir);

        /*var imagem = document.querySelector('#teste');
        imagem.src = elemento.url;
        imagem.style.top = elemento.posicao.y+'px';
        imagem.style.left = elemento.posicao.x+'px';
        imagem.style.width = elemento.widthImagem +'px';
        imagem.style.height =  elemento.heightImagem +'px';*/



    }
    else{

    }

}


function editarElemento(elemento){

    var novoelemento = JSON.parse(elemento);
    var elementoOrig = document.getElementById(novoelemento.id);

    elementoOrig.left = novoelemento.left;
    elementoOrig.top = novoelemento.top;
    elementoOrig.load();

}

function esconderElemento(elemento){
    console.log("esconderElemento = " + elemento);
    document.getElementById(elemento).style.display = 'none';
}

function mostrarCriarAplicacao(){


    document.getElementById('interacao').style.display = 'block';

}

function adicionarImagem(elemento){

    var dados = JSON.parse(elemento);
    Console.log('adicionarImagem com src = ' + dados.url);
    //document.getElementById('img').src = dados.src;

}

function testeFuncao(elemento){

    var imagem = document.querySelector('#teste');
    imagem.src = elemento.url;
    imagem.style.top = elemento.posicao.y+'px';
    imagem.style.left = elemento.posicao.x+'px';


    //console.log("elemento imagem  = " + document.getElementById('teste'));
    //document.getElementById("teste").src = elemento.url;


}