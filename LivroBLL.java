//package com.mycompany.desafio;
//
///**
// *
// * @author SONY VAIO
// */
//public class LivroBLL {
//public static void validaDados(Livro umlivro)
//{
// Erro.setErro(false);
// if (umlivro.getTitulo().equals(""))
//   {Erro.setErro("O campo TITULO é de preenchimento obrigatório..."); return;}
// if (umlivro.getAutor().equals(""))
//   {Erro.setErro("O campo AUTOR é de preenchimento obrigatório..."); return;}
// if (umlivro.getEditora().equals(""))
//   {Erro.setErro("O campo EDITORA é de preenchimento obrigatório..."); return;}
// if (umlivro.getAnoEdicao().equals(""))
//   {Erro.setErro("O campo ANO EDICAO é de preenchimento obrigatório..."); return;}
//  try {
//        Integer.valueOf(umlivro.getAnoEdicao()); 
//    } catch (NumberFormatException e) {
//        Erro.setErro("O campo ANO EDICAO deve ser numérico!");
//        return;
//    }
// if (umlivro.getLocalizacao().equals(""))
//   {Erro.setErro("O campo LOCALIZACAO é dxe preenchimento obrigatório..."); return;}
// LivroDAL.conectarFirebase();
// //if (Erro.getErro()) return;
// //LivroDAL.inserirLivro(umlivro);
//}    
//
//public static void validaTitulo(Livro umlivro)
//{
// Erro.setErro(false);
// if (umlivro.getTitulo().equals(""))
//   {Erro.setErro("O campo TITULO é de preenchimento obrigatório..."); return;}
// LivroDAL.conectarFirebase();
// //if (Erro.getErro()) return;
//    //String titulo = null;
// //LivroDAL.consultarLivro(titulo);
//}
//}
