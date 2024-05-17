package com.senac.navegacaoviagem.Componentes

import Cadastro
import Viagem
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.senac.navegacaoviagem.Login

@Composable
fun Navegacao(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { Login(context = LocalContext.current, navController = navController) }
        composable("cadastro") { Cadastro(navController = navController) }
        composable("paginainicial") { Viagem(navController = navController) }
    }
}