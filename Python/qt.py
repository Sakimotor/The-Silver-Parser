from PyQt5 import QtGui, QtCore
from PyQt5.QtWidgets import (QApplication, QCheckBox, QComboBox, QDateTimeEdit,
        QDial, QDialog, QGridLayout, QGroupBox, QHBoxLayout, QLabel, QLineEdit,
        QProgressBar, QPushButton, QRadioButton, QScrollBar, QSizePolicy,
        QSlider, QSpinBox, QStyleFactory, QTableWidget, QTabWidget, QTextEdit,
        QVBoxLayout, QWidget, QAbstractScrollArea, QTableWidgetItem, QHeaderView, QStackedLayout, QInputDialog, QFileDialog)
import sys
import re
import os


class Window(QWidget):
    def __init__(self):
        super().__init__()

        self.title = "The Silver Parser"
        self.top = 100
        self.left = 100
        self.width = 1300
        self.height = 900

        self.InitWindow()
        
        self.creatingTables()
        self.creatingButtons()
        mainLayout = QGridLayout()
        mainLayout.addWidget(self.tableWidget, 1, 0)
        mainLayout.addWidget(self.buttonsWidget, 0, 1)
        mainLayout.addWidget(self.buttonsWidget2, 0, 0)
        self.setLayout(mainLayout)

        self.language = "English"
        self.place = "Messages"


    def InitWindow(self):
        self.setWindowIcon(QtGui.QIcon("icon.png"))
        self.setWindowTitle(self.title)
        self.setGeometry(self.top, self.left, self.width, self.height)

    def creatingTables(self):
        # Définition des propriétés du tableau
        self.tableWidget = QTableWidget()
        self.tableWidget.setRowCount(0)
        self.tableWidget.setColumnCount(1)
        self.tableWidget.setHorizontalHeaderLabels(['Text'])
        self.tableWidget.setSizeAdjustPolicy(QAbstractScrollArea.AdjustToContents)
        # Mise en page du tableau
        header = self.tableWidget.horizontalHeader()   
        header.setSectionResizeMode(0, QHeaderView.Stretch)
        header2 = self.tableWidget.verticalHeader()
        header2.setSectionResizeMode(QHeaderView.ResizeToContents)   



    def loadFile(self):
        options = QFileDialog.Options()
        options |= QFileDialog.DontUseNativeDialog
        fileName, _ = QFileDialog.getOpenFileName(self,"Loading File", "","Game Files (*.txt);;All Files (*)", options=options)
        if fileName:
            self.filePath = fileName
            if self.tableWidget.rowCount() > 0:
                self.tableWidget.setRowCount(0)
            self.file = open(fileName, "r", encoding="utf-8")
            content_ori = self.file.read()
            self.file.close()
            self.content_ori = content_ori
            content_list = re.findall("messageEN\":\".*?(?=\")*\",\"seList\"", self.content_ori)
            content = '\n'.join(content_list)
            content_regex = re.sub("(?<!\\\\)\\\"", "", content)
            content_regex = re.sub("messageEN:", "", content_regex)
            content_regex = re.sub(",seList", "", content_regex)
            content_final = re.split("\n", content_regex)
            i = 0
            for n in content_final:
                self.tableWidget.insertRow(i)
                self.tableWidget.setItem(i, 0, QTableWidgetItem(n))
                i += 1
        else:
            if self.filePath:
                self.reloadFile()
            else:
                self.loadFile()        

    def reloadFile(self):
            if self.tableWidget.rowCount() > 0:
                self.tableWidget.setRowCount(0)
            self.file = open(self.filePath, "r", encoding="utf-8")
            content_ori = self.file.read()
            self.file.close()
            self.content_ori = content_ori
            if self.place == 'Messages':
                if self.language == 'English':
                    content_list = re.findall("messageEN\":\".*?(?=\")*\",\"seList\"", self.content_ori)
                    content = '\n'.join(content_list)
                    content_regex = re.sub("(?<!\\\\)\\\"", "", content)
                    content_regex = re.sub("messageEN:", "", content_regex)
                    content_regex = re.sub(",seList", "", content_regex)
                    content_final = re.split("\n", content_regex)

                else:
                    content_list = re.findall("messageJP\":\".*?(?=\")*\",", self.content_ori)
                    content = '\n'.join(content_list)
                    content_regex = re.sub("(?<!\\\\)\\\"", "", content)
                    content_regex = re.sub("messageJP:", "", content_regex)
                    content_regex = re.sub(",", "", content_regex)
                    content_final = re.split("\n", content_regex)                    


            else:
                if self.language == 'English':
                    content_list = re.findall("stringEN\":\".+?(?=\")*\"", self.content_ori)
                    content = '\n'.join(content_list)
                    content_regex = re.sub("\\\"", "", content)
                    content_regex = re.sub("stringEN:", "", content_regex)
                    content_final = re.split("\n", content_regex)
                else:
                    content_list = re.findall("stringJP\":\".+?(?=\")*\"", self.content_ori)
                    content = '\n'.join(content_list)
                    content_regex = re.sub("\\\"", "", content)
                    content_regex = re.sub("stringJP:", "", content_regex)
                    content_final = re.split("\n", content_regex)               


            i = 0
            for n in content_final:
                self.tableWidget.insertRow(i)
                self.tableWidget.setItem(i, 0, QTableWidgetItem(n))
                i += 1                        

    def saveFile(self):
        options = QFileDialog.Options()
        options |= QFileDialog.DontUseNativeDialog
        fileName, _ = QFileDialog.getSaveFileName(self,"Saving File", "","Game Files (*.txt)", options=options)
        if fileName:
            if fileName.endswith(".txt") == False:
                fileName += ".txt"
            content = []
            for n in range(0, self.tableWidget.rowCount()):
                content.append([])
                content[n].append(self.tableWidget.item(n, 0).text())
            content_sub = self.content_ori
            if self.place == 'Messages':
                if self.language == 'English':    
                    for m in range(0, len(content)):
                        content_sub = re.sub("messageEN\":\".*?(?=\")*\",\"seList\"", "messageENG\":\"" + re.sub(r"(\[(\'|\")|(\'|\")\])", "",str(content[m])) + "\",\"seList\"", content_sub, 1) 
                    content_sub = re.sub("messageENG", "messageEN", content_sub)
                else:
                    for m in range(0, len(content)):
                        content_sub = re.sub("messageJP\":\".*?(?=\")*\",", "messageJPN\":\"" + re.sub(r"(\[(\'|\")|(\'|\")\])", "",str(content[m])) + "\",", content_sub, 1) 
                    content_sub = re.sub("messageJPN", "messageJP", content_sub)

            else:
                if self.language == 'English':    
                    for m in range(0, len(content)):
                        content_sub = re.sub("stringEN\":\".*?(?=\")*\"", "stringENG\":\"" + re.sub(r"(\[(\'|\")|(\'|\")\])", "",str(content[m])) + "\"", content_sub, 1) 
                    content_sub = re.sub("stringENG", "stringEN", content_sub)
                else:
                    for m in range(0, len(content)):
                        content_sub = re.sub("stringJP\":\".*?(?=\")*\"", "stringJPN\":\"" + re.sub(r"(\[(\'|\")|(\'|\")\])", "",str(content[m])) + "\"", content_sub, 1) 
                    content_sub = re.sub("stringJPN", "stringJP", content_sub)
                        

            content_sub = re.sub("\"\"", "\"", content_sub)
            content_sub = content_sub.replace("\\\'", "\'")
            saved_file = open(fileName, "w", encoding="utf-8")
            saved_file.write(content_sub)


    def creatingButtons(self):
        self.buttonsWidget = QGroupBox()
        loadButton = QPushButton("Load File")
        loadButton.setDefault(True)
        saveButton = QPushButton("Save File")
        saveButton.setDefault(True)
        layout = QHBoxLayout()
        layout.addWidget(loadButton)
        layout.addWidget(saveButton)
        loadButton.setMaximumWidth(150)
        saveButton.setMaximumWidth(150)
        loadButton.clicked.connect(self.loadFile)
        saveButton.clicked.connect(self.saveFile)
        layout.setAlignment(QtCore.Qt.AlignCenter)
        self.buttonsWidget.setLayout(layout)


        self.buttonsWidget2 = QGroupBox()
        language = QComboBox()
        language.addItems(['English', 'Japanese'])
        langLabel = QLabel("&Language:")
        langLabel.setBuddy(language)
        place = QComboBox()
        place.addItems(['Messages', 'Places'])
        placeLabel = QLabel("&Type:")
        placeLabel.setBuddy(language)
        layout2 = QHBoxLayout()
        layout2.addWidget(langLabel)
        layout2.addWidget(language)
        layout2.addWidget(placeLabel)
        layout2.addWidget(place)
        language.setMaximumWidth(300)
        place.setMaximumWidth(300)
        language.activated[str].connect(self.changeLanguage)
        place.activated[str].connect(self.changePlace)
        layout2.setAlignment(QtCore.Qt.AlignCenter)
        self.buttonsWidget2.setLayout(layout2)
    	    

    def changeLanguage(self, language):
        self.language = language
        self.reloadFile()

    def changePlace(self, place):
        self.place = place
        self.reloadFile()    
 

App = QApplication([])
App.setStyle("Fusion")
window = Window()
window.show()
sys.exit(App.exec())
