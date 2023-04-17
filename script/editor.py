import os
import re
import sys
from PyQt5.QtWidgets import QApplication, QWidget, QLabel, QLineEdit, QPushButton, QVBoxLayout


class MyForm(QWidget):
    def __init__(self):
        super().__init__()

        self.setWindowTitle('Wordpress App')
        self.setGeometry(100, 100, 400, 200)

        layout = QVBoxLayout()

        self.label1 = QLabel('App Name')
        self.text1 = QLineEdit()
        layout.addWidget(self.label1)
        layout.addWidget(self.text1)

        self.label2 = QLabel('Package')
        self.text2 = QLineEdit()
        layout.addWidget(self.label2)
        layout.addWidget(self.text2)

        self.label3 = QLabel('Logo URL')
        self.text3 = QLineEdit()
        layout.addWidget(self.label3)
        layout.addWidget(self.text3)

        self.label4 = QLabel('Endpoint URL')
        self.text4 = QLineEdit()
        layout.addWidget(self.label4)
        layout.addWidget(self.text4)

        self.button = QPushButton('Submit', self)
        self.button.clicked.connect(self.submit)
        layout.addWidget(self.button)

        self.setLayout(layout)


    def replace_in_file(self, filepath, pattern, replacement):
        with open(filepath, 'r') as f:
            content = f.read()

        new_content = re.sub(pattern, replacement, content)

        with open(filepath, 'w') as f:
            f.write(new_content)

    def submit(self):
        appName = self.text1.text()
        pck = self.text2.text()
        logoUrl = self.text3.text()
        endpoint = self.text4.text()
        print(f'Field 1: {appName}')
        print(f'Field 2: {pck}')
        print(f'Field 3: {logoUrl}')
        print(f'Field 4: {endpoint}')


        # Get the directory of the current script

        script_path = os.path.dirname(os.path.realpath(__file__))

        if getattr(sys, 'frozen', False):
            # we are running in a bundle
            script_path = os.path.dirname(sys.executable)
        
            # we are running in a normal Python environment
        # for pyinstaller   script_path = os.path.dirname(sys.executable)

        print(f'script_path : {script_path}')

        self.replace_in_file(f'{script_path}/../app/src/main/java/com/teb/wordpressapp/WordpressApplication.kt', r'ENDPOINT = ".*"', 'ENDPOINT = "'+ endpoint + '"')
        self.replace_in_file(f'{script_path}/../app/src/main/java/com/teb/wordpressapp/WordpressApplication.kt', r'LOGO_URL = ".*"', 'LOGO_URL = "'+ logoUrl + '"')
        self.replace_in_file(f'{script_path}/../app/src/main/java/com/teb/wordpressapp/WordpressApplication.kt', r'import.*R', 'import '+ pck + '.R')
        self.replace_in_file(f'{script_path}/../app/src/main/AndroidManifest.xml', r'package=.*"', 'package="'+ pck + '"')
        self.replace_in_file(f'{script_path}/../app/src/main/res/values/app_config.xml', r'\<string name="app_name">.*\>', '<string name="app_name">'+ appName + '</string>')



        
 

if __name__ == '__main__':
    app = QApplication(sys.argv)
    form = MyForm()
    form.show()
    sys.exit(app.exec_())
