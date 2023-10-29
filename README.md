# TODO

create an installer by os (and manage to share openJDK17)

# NekoV2

![Neko](https://github.com/Aqueuse/NekoV2/blob/master/icon.png)

NekoV2 is desktop kitty application. Your cat will chase his ball of yarn around the screen,
and he will go to sleep in his basket as soon as he becomes tired.

## Packaging

# Windows 10
```
jpackage --input . --name NekoV2  --main-jar .\NekoV2.jar --icon .\neko.ico --license-file .\LICENSE.txt --app-version 2.0 --description "Neko application for the desktop" --win-dir-chooser --win-shortcut  --win-menu --type msi
```

# Linux Debian
```
jpackage --input . --name NekoV2  --main-jar NekoV2.jar --icon icon.png --linux-shortcut --license-file LICENSE --app-version 2.0 --description "Neko application for the desktop" --type deb
```


## Installation 

* Download the `.jar` file, and the `run.bat` file on the last release page.

* Be sure to have a Java Development Kit 15 or 16 installed.
  You should be able to find the `java.exe` command on the command line.
  
  => To download the JDK 16 : https://www.oracle.com/java/technologies/javase-jdk16-downloads.html

* Execute the `run.bat` file or double-click the jar file.
