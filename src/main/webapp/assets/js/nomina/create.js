$(function(){
    $('#newNomina').validate({
        rules :{
            datefield : {
                required : true,
                minlength : 10, //para validar campo con minimo 3 caracteres
                maxlength : 10  //para validar campo con maximo 9 caracteres
            },
            salariofield : {
                required : true,
                number : true   //para validar campo solo numeros
            },
            retencionfield : {
                required : true,
                number : true   //para validar campo solo numeros
            },
        }
    });    
});
