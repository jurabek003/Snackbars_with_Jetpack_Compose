package uz.turgunboyevjurabek.snackbarswithjetpackcompose

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.graphics.vector.ImageVector
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
                    openAlertDialogExample()
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
            openAlertDialogExample()
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
                .padding(horizontal = 30.dp, vertical = 30.dp)
        ) {
            TextField(value = textfaildState, label = {
                Text(text = "Enter your name")
            }, onValueChange = {
                textfaildState=it
            },
                singleLine = true,
                modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
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
        }
    }


}

@Composable
fun alertDialogExample( onDismissRequest: () -> Unit,
                        onConfirmation: () -> Unit,
                        context:Context,
                        title:String,
                        text:String,
                        icon:ImageVector)
{
    AlertDialog(onDismissRequest = { Toast.makeText(context, "onDismiss", Toast.LENGTH_SHORT).show() }, confirmButton = {
        Toast.makeText(context, "Confirm", Toast.LENGTH_SHORT).show() }, icon = {icon },
        title = { Text(text = title)}, text = { Text(text = text)})
}

@Composable
fun openAlertDialogExample(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var showDialog by remember { mutableStateOf(false) }
        val context= LocalContext.current
        if (showDialog){
            alertDialogExample(
                onDismissRequest = {showDialog=false },
                onConfirmation = { showDialog=false },
                context = context,
                title = "Assalom alekum",
                text = "Android app with Jetpack Compose",
                icon = Icons.Default.Star
            )
        }
        Button(onClick = { showDialog = true },) {
            Text(text = "AlertDialog")
        }

    }

}