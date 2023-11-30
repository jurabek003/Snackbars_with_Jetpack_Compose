package uz.turgunboyevjurabek.snackbarswithjetpackcompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import uz.turgunboyevjurabek.snackbarswithjetpackcompose.ui.theme.SnackbarsWithJetpackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current
            SnackbarsWithJetpackComposeTheme {
                Column( horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 30.dp)) {
                    snacbar()
                }



             }
           }
        }
    }


@Preview(showSystemUi = true)
@Composable
fun GreetingPreview() {
    SnackbarsWithJetpackComposeTheme {
        Column {
            snacbar()
        }

    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun snacbar(){
    var textfaildState by remember{
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackbarHostState)
    }) {
        Column(

            modifier = Modifier
                .padding(horizontal = 30.dp, vertical = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextField(value = textfaildState, label = {
                Text(text = "Enter your name")
            }, onValueChange = {
                textfaildState=it
            },
                singleLine = true,
                modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))

            var showDialog by remember { mutableStateOf(false) }

            Row(horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()) {
                Button(onClick = {

                    scope.launch {

                        val result=snackbarHostState.showSnackbar(message = textfaildState,
                            actionLabel = "Action",
                            duration = SnackbarDuration.Short)
                        when(result){
                            SnackbarResult.ActionPerformed->{
                                // Toast.makeText(context, "Assalom alekum", Toast.LENGTH_SHORT).show()
                            }
                            SnackbarResult.Dismissed->{

                            }
                        }
                    }
                }) {
                    Text(text = "Pls greet me")
                }
                Button(onClick = { showDialog = true },) {
                    Text(text = "AlertDialog")
                }

            }
                val context= LocalContext.current
                if (showDialog){
                    AlertDialog(
                        onDismissRequest = {showDialog=false },
                        title = { Text(text = "Assalom alekum",)},
                        text = { Text(text = "Android app with Jetpack Compose",)},
                        dismissButton = {
                            Button(onClick = {showDialog=false}, modifier = Modifier.background(
                                Color.White)) {
                                Text(text = "Cancel", color = Color.Black)
                            }
                        },
                        confirmButton = {
                            Button(onClick = { showDialog=false }, modifier = Modifier.background(
                                Color.White)) {
                                Text(text = "Ok", color = Color.Black)
                            }
                        }
                    )
                }

        }
    }


}