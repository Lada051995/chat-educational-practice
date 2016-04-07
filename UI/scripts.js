/**
 * Created by Yoga 3 Pro on 13.03.2016.
 */

var messageOption = function (text, value) {
    return {
        message: text,
        id: value
    };
};

var fullName = function (name) {
    return {
        name: name
    };
};

var listForSavingMessages = [];


function run() {
    var send = document.getElementById('send');
    var rename = document.getElementById('rename');
    var onOff = document.getElementById('buttonOnOff');
    var deleteMessage = document.getElementById('delete');
    var editMessage = document.getElementById('edit');

    send.addEventListener('click', EventSend);
    rename.addEventListener('click', EventRename);
    onOff.addEventListener('click', EventOnOff);
    deleteMessage.addEventListener('click', EventDelete);
    editMessage.addEventListener('click', EventEdit);

    if (restoreName() != null){
        var nameAndSurname = restoreName();
        createNameSurname(nameAndSurname);
    }

    if(restoreMessages()!=null) {
        var allMessages = restoreMessages();
        createAllMessages(allMessages);
    }
}


function EventRename(evtObj) {
    var nameSurname = document.getElementById('nameSurname');
    var rename = document.getElementById('areaRename');

    if (rename.value) {
        nameSurname.value = rename.value;
        rename.value = "";
    }
    if(nameSurname.value) {
        storeName(fullName(nameSurname.value));
    }

}

function EventOnOff(evtObj) {
    var onOff = document.getElementById('buttonOnOff');

    if (onOff.className == 'btn btn-success') {
        onOff.className = 'btn btn-danger';
        onOff.value = "Off";
    }
    else {
        onOff.className = 'btn btn-success';
        onOff.value = "On";
    }
}

function EventSend(evtObj) {
    var areatext = document.getElementById('myTextArea');
    var textArea = document.getElementById('nameSurname');
    var selected = document.getElementById('sel1');
    if (areatext.value && textArea.value) {
        var ind = 0;
        for (var i = 0; i < selected.length; i++) {
            var index = document.getElementById('sel1').selectedIndex = i.toString();
            var select = document.getElementById('sel1')[index];
            if (select.text.indexOf("*editing process*") != -1) {
                ind = i;
            }
        }
        if (ind == 0) {
            var option = document.createElement("option");
            option.text = textArea.value + ": " + areatext.value;
            option.value = selected.length;
            selected.add(option);
            areatext.value = "";

            listForSavingMessages.push(messageOption(option.text, option.value));
            storeMessages(listForSavingMessages);
        }
        else {
            var select = document.getElementById('sel1')[ind];
            select.text = textArea.value + ": " + areatext.value + " /Edit";
            areatext.value = "";

            listForSavingMessages[ind] = messageOption(select.text, ind);
            storeMessages(listForSavingMessages);

        }

    }
}

function EventDelete(evtObj) {
    var n_s = document.getElementById('nameSurname');
    var index = document.getElementById('sel1').selectedIndex;
    var select = document.getElementById('sel1')[index];
    var subindex = select.text.indexOf(":");
    if (n_s.value == select.text.substring(0, subindex)) {
        select.text = "Message has been deleted by " + n_s.value;
    }

    listForSavingMessages[index] = messageOption(select.text, index);
    storeMessages(listForSavingMessages);

}

function EventEdit (evtObj) {
    var n_s = document.getElementById('nameSurname');
    var areatext = document.getElementById('myTextArea');
    var index = document.getElementById('sel1').selectedIndex;
    var select = document.getElementById('sel1')[index];
    var subindex = select.text.indexOf (":");
    if(n_s.value == select.text.substring(0, subindex)&&select.text.indexOf("Message has been deleted by ") == -1){
        var substring1 = select.text.substring(subindex+1, select.length);
        if (select.text.indexOf("/Edit") == -1){
            areatext.value = substring1;
            select.text = "*editing process*";
        }
        else {
            var ed = select.text.indexOf("/Edit");
            var substring2 = substring1.substring(0, substring1.length-5);
            areatext.value = substring2;
            select.text = "*editing process*";
        }

    }
}

function storeMessages(listForSavingMessages) {
    if(typeof (Storage) == "undefined") {
        alert('local storage is not accessible');
        return;
    }
    localStorage.setItem("list messages", JSON.stringify(listForSavingMessages));
}

function restoreMessages() {
    if(typeof (Storage) == "undefined") {
        alert('local storage is not accessible');
        return;
    }
    var item = localStorage.getItem("list messages");
    return item && JSON.parse(item);
}

function createAllMessages(allMessages) {
    for (var i = 0; i < allMessages.length; i++ ) {
        listForSavingMessages.push(allMessages[i]);
        addMessage(allMessages[i]);
    }
}

function addMessage(message) {
    var selected = document.getElementById('sel1');
    var option = document.createElement("option");
    option.text = message.message;
    option.value = message.id;

    selected.add(option);
}

function storeName(mystruct){
    if (typeof (Storage) == "undefined"){
        alert('local storage is not accessible');
        return;
    }
    localStorage.setItem("MyName", JSON.stringify(mystruct));
}

function restoreName(){
    if(typeof (Storage) == "undefined"){
        alert('local storage in not accessible');
        return;
    }
    var item = localStorage.getItem("MyName");
    return item && JSON.parse(item);
}

function createNameSurname (nameAndSurname) {
    var textArea = document.getElementById('nameSurname');
    textArea.value = nameAndSurname.name;
}