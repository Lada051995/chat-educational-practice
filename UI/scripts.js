/**
 * Created by Yoga 3 Pro on 13.03.2016.
 */
function run () {
    var send = document.getElementById('send');
    var rename = document.getElementById('rename');
    var onOff = document.getElementById('buttonOnOff');
    var deleteMessage = document.getElementById('delete');

    send.addEventListener ('click', EventSend);
    rename.addEventListener('click', EventRename);
    onOff.addEventListener('click', EventOnOff);
    deleteMessage.addEventListener('click', EventDelete);
}

function EventSend(evtObj) {
    var areatext = document.getElementById('myTextArea');
    var textArea = document.getElementById('nameSurname');
    if (areatext.value&&textArea.value) {
        debugger
        var selected = document.getElementById('sel1');
        var option = document.createElement("option");
        option.text = textArea.value + ": " +areatext.value;
        option.value = selected.length;
        selected.add(option);
        areatext.value = "";
    }
}

function EventRename(evtObj) {
    var nameSurname = document.getElementById('nameSurname');
    var rename = document.getElementById('areaRename');

    if(rename.value){
        nameSurname.value = rename.value;
        rename.value = "";
    }
}

function EventOnOff(evtObj) {
    var onOff = document.getElementById('buttonOnOff');
    if(onOff.className == 'btn btn-success'){
        onOff.className = 'btn btn-danger';
        onOff.value = "Off";
    }
    else {
        onOff.className = 'btn btn-success';
        onOff.value = "On";
    }
}

function EventDelete (evtObj) {
    var n_s = document.getElementById('nameSurname');
    var index = document.getElementById('sel1').selectedIndex;
    var select = document.getElementById('sel1')[index];
    var subindex = select.text.indexOf (":");
    if(n_s.value == select.text.substring(0, subindex)){
        select.text = "Message has been deleted by " + n_s.value;
    }

}
