from PySide6.QtWidgets import QApplication, QWidget, QLabel
from PySide6.QtGui import QMovie
import sys


class Window(QWidget):
    def __init__(self, speed: float):
        super().__init__()

        self.setGeometry(100, 100, 1280, 720)
        self.setWindowTitle("Cone Tank")

        label = QLabel(self)
        movie = QMovie("coneTankHD.gif")
        movie.setSpeed(speed * 100)
        label.setMovie(movie)
        movie.start()


def play_gif(speed: float):
    app = QApplication([])
    window = Window(speed)
    window.show()
    sys.exit(app.exec())
