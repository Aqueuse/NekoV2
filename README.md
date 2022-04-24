# TODO

bug to fix :
   * dual screen toy access

# NekoV2

![Neko](https://github.com/Aqueuse/NekoV2/blob/master/icon.png)

NekoV2 is desktop kitty application. Your cat will chase his ball of yarn around the screen,
and he will go to sleep in his basket as soon as he becomes tired.

***Validated on Windows 10, with java 15 & 16.***

## Installation 

* Download installer for your OS in the release page : https://github.com/Aqueuse/NekoV2/releases/tag/2.0

* Do me a feedback if you have problems in the issue page : https://github.com/Aqueuse/NekoV2/issues

* If you love Neko, buy me a coffee here : https://ko-fi.com/aqueuse  ☕❤


## package creation

### Debian/Ubuntu

```jpackage --input . --name NekoV2  --main-jar NekoV2.jar --icon icon.png --linux-shortcut --license-file LICENSE --app-version 2.0 --description "Neko application for the desktop" --type deb```
