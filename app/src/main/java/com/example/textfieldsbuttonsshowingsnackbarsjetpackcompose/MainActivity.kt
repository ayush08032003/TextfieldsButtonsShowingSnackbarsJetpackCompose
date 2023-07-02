package com.example.textfieldsbuttonsshowingsnackbarsjetpackcompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            val scaffoldState = rememberScaffoldState()
//            Snackbar { // this is same as Toast, in native app development, but the main issue is, here simply using this can result in showing the data at the top of the screen fixing there.
//                Text(text = "Hello Ayush")
//            }

            val snackbarHostState = remember { SnackbarHostState() }
            var textFieldState by remember { mutableStateOf("") } // when using this, has to import androidx.compose.runtime.*
            val scope = rememberCoroutineScope()

            Scaffold(Modifier.fillMaxSize(), snackbarHost = {
                SnackbarHost(snackbarHostState) { data ->
                    Snackbar(
                        modifier = Modifier
                            .padding(12.dp)
                    ) {
                        Text(data.visuals.message)
                    }
                }
            }) // THis is the Basic standard of raising a SnackBar, A SnackBar is same as that of Toast in Native Android App.
            {
                Column( // Column is used for arranging the items in column fashioned way..
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    TextField(
                        value = textFieldState, // this is to always update the value of the field, as state is basically the thing which remembers the data
                        label = { Text("Enter your name") },
                        onValueChange = {
                            textFieldState = it
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = {
                        scope.launch { // since this showSnackBar is a suspended function, we need to make it inside a scope Coroutine..
                            snackbarHostState.showSnackbar("My name is $textFieldState")
                        }
                    }) {
                        Text("Show my name")
                    }
                }

            }
        }
    }
}

/*
NOTES:
Reference :  https://stackoverflow.com/questions/71363542/not-able-to-use-rememberscaffoldstate-in-android-compose-material3/76313062#76313062?newreg=35bce42d5232403581864c66af29ea6e
YT Link: https://www.youtube.com/watch?v=_yON9d9if6g&list=PLQkwcJG4YTCSpJ2NLhDTHhi6XBNfk9WiC&index=7&pp=iAQB

 This Tutorial is little different from what philipp Lackner has taught in his video, as android makes a little changes to it. 
 */