package com.example.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notes.destinations.MainScreenDestination
import com.example.notes.destinations.NoteScreenDestination
import com.example.notes.destinations.ShowNotesDestination
import com.example.notes.model.NoteContent
import com.example.notes.ui.AddNotesviewModel
import com.example.notes.ui.NoteViewModel
import com.example.notes.ui.componentes.SearchText
import com.example.notes.ui.theme.NotesTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            NotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    DestinationsNavHost(navGraph = NavGraphs.root)

                }
            }
        }
    }
}

@RootNavGraph(start = true)
@Destination
@Composable
fun MainScreen(nav: DestinationsNavigator, not: NoteViewModel = hiltViewModel()) {
    var info = remember {
        mutableStateOf(false)
    }
    var isSearching by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xff252525))
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {

        AnimatedVisibility(isSearching) {
            SearchText(not)
        }
        AnimatedVisibility(!isSearching) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                horizontalArrangement = Arrangement.End
            ) {

                Text(modifier = Modifier, text = stringResource(R.string.app_name), fontSize = 43.sp)

                Spacer(modifier = Modifier.padding(85.dp))
                Button(
                    onClick = { isSearching=true },
                    modifier = Modifier
                        .width(50.dp)
                        .height(40.dp),
                    shape = RoundedCornerShape(20),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff454545))

                ) {
                    Image(painter = painterResource(id = R.drawable.search), contentDescription = "بحث")
                }

                Spacer(
                    modifier = Modifier.padding(2.dp)
                )
                Button(
                    modifier = Modifier
                        .width(50.dp)
                        .height(40.dp),
                    onClick = {
                        info.value = true
                    },
                    shape = RoundedCornerShape(20),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff454545))

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.info_outline),
                        contentDescription = "التفاصيل"
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .clickable { isSearching = false }
        ) {
            Image(painter = painterResource(id = R.drawable.rafiki), contentDescription = "")
            Lazylist(nav)
        }

    }



    if (info.value) CardInfo(info)
}






@Destination
@Composable
fun CardInfo(info: MutableState<Boolean>) {
    MaterialTheme {

        Column() {
            var openD = remember { mutableStateOf(true) }

            if (openD.value) {
                AlertDialog(onDismissRequest = {
                    info.value = false
                    openD.value = info.value
                }, text = {
                    Column() {
                        Text(modifier = Modifier.width(200.dp), text = "Designed by -  ")
                        Text(modifier = Modifier.width(200.dp), text = "Redesigned by -  ")
                        Text(modifier = Modifier.width(200.dp), text = "Illustrations - ")
                        Text(modifier = Modifier.width(200.dp), text = "Icons - ")
                        Text(modifier = Modifier.width(200.dp), text = "Font -")
                    }
                }, buttons = { openD.value = true })
            }
        }
    }
}


@Destination
@Composable
fun NoteScreen(nav: DestinationsNavigator, viewModel: AddNotesviewModel = hiltViewModel()) {
    val titl = remember { mutableStateOf(TextFieldValue("")) }
    val cont = remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xff252525))
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
        ) {

            Button(
                onClick = { nav.navigate(MainScreenDestination) },
                modifier = Modifier
                    .width(50.dp)
                    .height(40.dp),
                shape = RoundedCornerShape(20),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff454545))

            ) {
                Image(
                    painter = painterResource(id = R.drawable.chevron_left),
                    contentDescription = "بحث"
                )
            }
            Spacer(modifier = Modifier.width(235.dp))

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .width(50.dp)
                    .height(40.dp),
                shape = RoundedCornerShape(20),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff454545))

            ) {
                Image(
                    painter = painterResource(id = R.drawable.visibility),
                    contentDescription = "بحث"
                )
            }

            Spacer(modifier = Modifier.width(2.dp))

            Button(
                onClick = {
                    viewModel.addNotes(titl.value, cont.value)
                    nav.popBackStack()
                },
                modifier = Modifier
                    .width(50.dp)
                    .height(40.dp),
                shape = RoundedCornerShape(20),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff454545))

            ) {
                Image(painter = painterResource(id = R.drawable.save), contentDescription = "حفظ")
            }
        }
//        FloatingAction

        TextField(value = titl.value,
            onValueChange = { titl.value = it },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(5.dp, 0.dp),
            textStyle = TextStyle(fontSize = 52.sp),
            placeholder = {
                Text(
                    "Title", fontSize = 52.sp, color = Color(0xff9A9A9A)
                )
            })

        TextField(value = cont.value,
            onValueChange = { cont.value = it },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(5.dp),
            textStyle = TextStyle(fontSize = 20.sp),
            maxLines = 35,
            placeholder = {
                Text(
                    "Type something...", fontSize = 20.sp, color = Color(0xff9A9A9A)
                )
            })


    }


}






@Destination
@Composable
fun ShowNotes(nav: DestinationsNavigator, getNote : NoteContent) {
    var tit by remember {
        mutableStateOf(getNote.title)
    }
    var cont by remember {
        mutableStateOf(getNote.note)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xff252525))
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start
        ) {

            Button(
                onClick = { nav.navigate(MainScreenDestination) },
                modifier = Modifier
                    .width(50.dp)
                    .height(40.dp),
                shape = RoundedCornerShape(20),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xff454545))

            ) {
                Image(
                    painter = painterResource(id = R.drawable.chevron_left),
                    contentDescription = "Back"
                )
            }
        }
        TextField(value = tit,
            onValueChange = { tit = it },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(5.dp, 0.dp),
            textStyle = TextStyle(fontSize = 52.sp),
            placeholder = {
                Text(
                    "Title", fontSize = 52.sp, color = Color(0xff9A9A9A)
                )
            })

        TextField(value = cont,
            onValueChange = { cont = it },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(5.dp),
            textStyle = TextStyle(fontSize = 20.sp),
            maxLines = 35,
            placeholder = {
                Text(
                    "Type something...", fontSize = 20.sp, color = Color(0xff9A9A9A)
                )
            })
        }
    }


@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun Lazylist(
    nav: DestinationsNavigator,
    viewModel: NoteViewModel = hiltViewModel(),
    addNote: AddNotesviewModel = hiltViewModel()
) {
    val randomColors = listOf(
        Color(0xfffd99ff),
        Color(0xff91f48f),
        Color(0xfffff599),
        Color(0xffff9e9e),
        Color(0xff9effff),
        Color(0xffb69cff)

    )

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier.fillMaxSize(),


        ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
        ) {

            items(viewModel.notes) { NoteContent ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 8.dp)
                        .background(randomColors.random())
                        .combinedClickable(onClick = { nav.navigate(ShowNotesDestination(NoteContent)) },
                            onLongClick = { addNote.deleteNote(NoteContent) })

                ) {
                    Text(text = "${NoteContent.title}")
                    Text(text = "${NoteContent.note}")

                }
            }
        }
        Box(
            modifier = Modifier
                .padding(end = 25.dp, bottom = 25.dp)
                .align(Alignment.BottomEnd)
                .background(Color.Transparent)
        ) {
            FloatingActionButton(
                shape = RoundedCornerShape(30.dp),
                backgroundColor = Color(0xff454545),
                onClick = { nav.navigate(NoteScreenDestination) }) {
                Icon(
                    imageVector = Icons.Rounded.Add, contentDescription = "", tint = Color.White
                )

            }
        }

    }
}




@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NotesTheme {}
}