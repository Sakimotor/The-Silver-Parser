# The Silver Parser



A GUI tool made in Java/Python to facilitate your journey through the .txt files from the games "The Silver Case" and "The 25Th Ward: The Silver Case".

<img src="https://i.imgur.com/qEYwyEo.png"
     alt="Java Version" />


## Requirements

Java version : 

- [Java 11](https://ninite.com/adoptjavax11/)

Python (.py file version): 

- [Python3](https://www.python.org/downloads/)
- [PyQt](https://pypi.org/project/PyQt5/)


## How To Use

Go to the [Releases](https://github.com/Sakimotor/The-Silver-Parser/releases) tab, then download the latest .jar or .exe release file.
Once you have executed the file, follow the instructions and load a file to start reading/editing the game dialogs/places. The first time you load a text file, the table may take some time to fully load, so if your Table is empty after loading a file, please wait up to a minute. Also, if it's your first time using Java or Python, don't forget to add them to your OS' Path [(for Java)](https://help.talend.com/reader/nvbM6kBZltEAlPPDszEwUA/mTSf7z1s7egd5xs6r_vhDw) and [(for Python)](https://datatofish.com/add-python-to-windows-path/)

**YOU CANNOT LOAD THE .ASSETS FILE DIRECTLY, EXTRACT THE .TXT FILES FROM IT WITH TOOLS SUCH AS [UNITY EX](https://forum.zoneofgames.ru/topic/36240-unityex/) OR [AssetStudio](https://github.com/Perfare/AssetStudio) !**


## Python Troubleshooting

In case you can't launch the .py file, make sure you meet the Requirements.

If you want to compile the program into an executable yourself, feel free to follow [this guide](https://pythonbasics.org/compile-pyqt-to-exe/)

## Java Troubleshooting

If you have trouble executing the Java program, try updating your JDK to Java 11 ([This link makes it easy to install](https://ninite.com/adoptjavax11/)), or compile it yourself for your target system With the following commands (do not forget to create a [manifest](https://docs.oracle.com/javase/tutorial/deployment/jar/manifestindex.html) file that precises "Parser" as the main class for your executable) :

```bash
javac -encoding UTF8 *.java
jar cmf MANIFEST.MF Parser.jar *.class
```

Last note, while editing the text you may notice there are &lt;html&gt; and &lt;\\html&gt; tags. **DO NOT TOUCH THEM** ! They are required in order for long strings to be displayed on multiple lines in the table. They are removed during the file saving process so nevermind them.

## For Developers

I've made a javadoc for my program. If you need to read it, use the following command :

```bash
javadoc -encoding UTF8 *.java
```

It will make a bunch of files, some of them being html files you will be able to open with your favorite web browser ! I recommend opening the "index-all.html" file if you need informations about any class or method.



## What's Next ?

PyQt version ? 👀