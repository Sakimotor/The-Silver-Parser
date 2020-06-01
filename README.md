# The-Silver-Parser



A GUI tool made in Java to facilitate your journey through the .txt files from the games "The Silver Case" and "The 25Th Ward: The Silver Case".

<img src="https://i.imgur.com/qEYwyEo.png"
     alt="The Silver Parser" />

## How To Use

Go to the [Releases](https://github.com/Sakimotor/The-Silver-Parser/releases) tab, then download the latest .jar release file.
Once you have executed the  .jar file, follow the instructions and load a file to translate. The first time you load a text file, the table may take some time to fully load, so if your Table is empty after loading a file, please wait up to a minute.

**YOU CANNOT LOAD THE .ASSETS FILE DIRECTLY, EXTRACT THE .TXT FILES FROM IT WITH TOOLS SUCH AS [UNITY EX](https://forum.zoneofgames.ru/topic/36240-unityex/) OR [AssetStudio](https://github.com/Perfare/AssetStudio) !**

If you have trouble executing the program, try updating your JDK to Java 11[This link makes it easy to install](https://ninite.com/adoptjavax11/), or compile it yourself for your target system With the following commands (do not forget to create a [manifest](https://docs.oracle.com/javase/tutorial/deployment/jar/manifestindex.html) file that precises "Parser" as the main class for your executable) :

```bash
javac -encoding UTF8 *.java
jar cmf MANIFEST.MF Parser.jar *.class
```

## For Developers

I've made a javadoc for my program. If you need to read it, use the following command :

```bash
javadoc -encoding UTF8 *.java
```

It will make a bunch of files, some of them being html files you will be able to open with your favorite web browser ! I recommend opening the "index-all.html" file if you need informations about any class or method.


