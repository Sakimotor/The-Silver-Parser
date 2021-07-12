from PyQt5 import QtGui, QtCore
from PyQt5.QtCore import QFile
from PyQt5.QtWidgets import (QApplication, QCheckBox, QComboBox, QDateTimeEdit,
		QDial, QDialog, QGridLayout, QGroupBox, QHBoxLayout, QLabel, QLineEdit,
		QProgressBar, QPushButton, QRadioButton, QScrollBar, QSizePolicy,
		QSlider, QSpinBox, QStyleFactory, QTableWidget, QTabWidget, QTextEdit,
		QVBoxLayout, QWidget, QAbstractScrollArea, QTableWidgetItem, QHeaderView, QStackedLayout, QInputDialog, QFileDialog, QMessageBox, QStyledItemDelegate)
import sys
import re
import os
import json

# https://learndataanalysis.org/make-certain-rows-or-columns-read-only-on-a-table-widget-in-pyqt5/
class ReadOnlyDelegate(QStyledItemDelegate):
	def createEditor(self, parent, option, index):
		return 


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
		# Defining table proprieties
		self.tableWidget = QTableWidget()
		self.tableWidget.setRowCount(0)
		self.tableWidget.setColumnCount(2)
		self.tableWidget.setHorizontalHeaderLabels(['ID', 'Text'])
		self.tableWidget.setSizeAdjustPolicy(QAbstractScrollArea.AdjustToContents)
		delegate = ReadOnlyDelegate(self)
		self.tableWidget.setItemDelegateForColumn(0, delegate)
		# Mise en page du tableau
		header = self.tableWidget.horizontalHeader()   
		header.setStretchLastSection(True)

		header2 = self.tableWidget.verticalHeader()
		header2.setSectionResizeMode(QHeaderView.ResizeToContents)   



	def loadFile(self):
		options = QFileDialog.Options()
		options |= QFileDialog.DontUseNativeDialog
		# Open the JSON file
		fileName, _ = QFileDialog.getOpenFileName(self,"Loading File", "","Game Files (*.txt);;Game Files (*.json);;All Files (*)", options=options)
		if fileName:
			self.filePath = fileName
			if self.tableWidget.rowCount() > 0:
				self.tableWidget.setRowCount(0)
			self.file = open(fileName, "r", encoding="utf-8")
			# Define the dictionnaries that will stock the data we want form JSON
			self.jayson = json.load(self.file)
			self.messages = self.jayson["dic"]
			self.places = self.jayson["stringDic"]
			self.file.close()
			i = 0
			for id in self.messages:
				self.tableWidget.insertRow(i)
				self.tableWidget.setItem(i, 1, QTableWidgetItem(self.messages[id]['messageEN']))
				self.tableWidget.setItem(i, 0, QTableWidgetItem(id))
				i += 1
		else:
			if self.filePath:
				self.reloadFile()
			else:
				self.loadFile()		

	def reloadFile(self):
			# Reset the whole table
			if self.tableWidget.rowCount() > 0:
				self.tableWidget.setRowCount(0)
			i = 0
			# Display the values accordingly to what we want
			if self.place == 'Messages':
				if self.language == 'English':
					for id in self.messages:
						self.tableWidget.insertRow(i)
						self.tableWidget.setItem(i, 1, QTableWidgetItem(self.messages[id]['messageEN']))
						self.tableWidget.setItem(i, 0, QTableWidgetItem(id))
						i += 1
				else:
					for id in self.messages:
						self.tableWidget.insertRow(i)
						self.tableWidget.setItem(i, 1, QTableWidgetItem(self.messages[id]['messageJP']))
						self.tableWidget.setItem(i, 0, QTableWidgetItem(id))
						i += 1					

			else:
				if self.language == 'English':
					for id in self.places:
						self.tableWidget.insertRow(i)
						self.tableWidget.setItem(i, 1, QTableWidgetItem(self.places[id]['stringEN']))
						self.tableWidget.setItem(i, 0, QTableWidgetItem(id))
						i += 1
				else:
					for id in self.places:
						self.tableWidget.insertRow(i)
						self.tableWidget.setItem(i, 1, QTableWidgetItem(self.places[id]['stringJP']))
						self.tableWidget.setItem(i, 0, QTableWidgetItem(id))
						i += 1		   


						

	def saveFile(self):
		# Select where we save the end file
		options = QFileDialog.Options()
		options |= QFileDialog.DontUseNativeDialog
		fileName, _ = QFileDialog.getSaveFileName(self,"Saving File", "","Game Files (*.txt);;Game Files (*.json)", options=options)
		if fileName:
			#Verify that the file ends with the right extension
			if (fileName.endswith(".txt") == False) and (fileName.endswith(".json") == False) :
				fileName += ".txt"
				if (QFile.exists(fileName)) and (QMessageBox.Yes != QMessageBox.question(self, "", "Confirm overwrite?", QMessageBox.Yes | QMessageBox.No)):
					return None 
	  
	
			# Replace the JSON values by the ones in the table
			content = {}
			for n in range(0, self.tableWidget.rowCount()):
				content[self.tableWidget.item(n, 0).text()] = self.tableWidget.item(n, 1).text()
			if self.place == 'Messages':
				if self.language == 'English':	
					for id in content:
						self.messages[id]['messageEN'] = content[id]
				else:
					for id in content:
						self.messages[id]['messageJP'] = content[id]

			else:
				if self.language == 'English':	
					for id in content:
						self.places[id]['stringEN'] = content[id]
				else:
					for id in content:
						self.places[id]['stringJP'] = content[id]
						

			self.jayson['dic'] = self.messages
			self.jayson['stringDic'] = self.places
			# Save the result
			saved_file = open(fileName, "w", encoding='utf-8')
			json.dump(self.jayson, saved_file, ensure_ascii=False, separators=(',', ':'))
			saved_file.close()


	def creatingButtons(self):
		# More GUI stuff
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

		# Still the GUI
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
