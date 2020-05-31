# The-Silver-Parser

A GUI tool made in Java to make translation of "The Silver Case" easier

## How To Use

Go to the [Releases](https://github.com/Sakimotor/The-Silver-Parser/releases) tab, then download the latest .jar release file.
Once you have executed the download .jar program, follow the instructions and load a file to translate.
**####YOU CANNOT LOAD THE .ASSETS FILE DIRECTLY, EXTRACT IT WITH TOOLS SUCH AS [UNITY EX](https://forum.zoneofgames.ru/topic/36240-unityex/) OR [AssetStudio](https://github.com/Perfare/AssetStudio) !**

If you have trouble executing the program, try updating your JDK to Java 11, or compile it yourself for your target system With the following commands (do not forget to create a [manifest](https://docs.oracle.com/javase/tutorial/deployment/jar/manifestindex.html) file that precises "Parser" as the main class for your executable) :

```bash
javac *.java
jar cfm MANIFEST.MF Parser.jar *.class
```
