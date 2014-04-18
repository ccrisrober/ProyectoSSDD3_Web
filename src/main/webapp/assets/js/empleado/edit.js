/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(function(){
    $('#updForm').validate({
        rules :{
            userfield : {
                required : true,
                minlength : 3, //para validar campo con minimo 3 caracteres
                maxlength : 9  //para validar campo con maximo 9 caracteres
            },
            passwordfield : {
                required : true,
                minlength : 3, //para validar campo con minimo 3 caracteres
                maxlength : 9  //para validar campo con maximo 9 caracteres
            },
            namefield : {
                required : true,
                minlength : 3, //para validar campo con minimo 3 caracteres
                maxlength : 9  //para validar campo con maximo 9 caracteres
            },
            surnamefield : {
                required : true,
                minlength : 3, //para validar campo con minimo 3 caracteres
                maxlength : 9  //para validar campo con maximo 9 caracteres
            },
            dnifield : {
                required : true,
                minlength : 3, //para validar campo con minimo 3 caracteres
                maxlength : 9  //para validar campo con maximo 9 caracteres
            },
            departamentfield : {
                required : true,
                minlength : 3, //para validar campo con minimo 3 caracteres
                maxlength : 9  //para validar campo con maximo 9 caracteres
            },
            sucursalfield : {
                required :true,
            }

        }
    });    
});
