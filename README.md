# The-Silver-Parser

<img src="https://i.imgur.com/07oniKw.png" 
     alt="The Silver Parser" />

A GUI tool made in Java to make translation of "The Silver Case" easier. Right now, this tools supports any modern version of "The Silver Case". If you use the tool for "The 25Th Ward", it may work technically but you may encounter some glitchy lines because of empty messages left by the developpers (messageEN"" really does harm on my regex). Example :
<img src="https://i.imgur.com/sdGUphP.png"
     alt="A Great Example"
     style="float: left; margin-right: 10px;" />

## How To Use

Go to the [Releases](https://github.com/Sakimotor/The-Silver-Parser/releases) tab, then download the latest .jar release file.
Once you have executed the  .jar file, follow the instructions and load a file to translate. The first time you load a text file, the table may take some time to get loaded, so if your Table is empty after loading a file, please wait up to a minute.

**YOU CANNOT LOAD THE .ASSETS FILE DIRECTLY, EXTRACT THE .TXT FILES FROM IT WITH TOOLS SUCH AS [UNITY EX](https://forum.zoneofgames.ru/topic/36240-unityex/) OR [AssetStudio](https://github.com/Perfare/AssetStudio) !**

If you have trouble executing the program, try updating your JDK to Java 11, or compile it yourself for your target system With the following commands (do not forget to create a [manifest](https://docs.oracle.com/javase/tutorial/deployment/jar/manifestindex.html) file that precises "Parser" as the main class for your executable) :

```bash
javac *.java
jar cmf MANIFEST.MF Parser.jar *.class
```
