from math import sqrt, tan, pi
import numpy
import matplotlib.pyplot as plt
import numpy as np


def calculate_t(H: float, theta: float, d: float) -> float:
    r = d / 2
    return 2 / 5 * H ** (5 / 2) * tan(theta) ** 2 / (r ** 2 * sqrt(2 * 9.81))


def draw_plot(t: float, H: float, theta: float):
    arr_t = np.linspace(0, t, 50)
    arr_h = np.linspace(H, 0, 50)
    arr_r = arr_h * tan(theta)
    arr_v = 1/3 * arr_h * pi * arr_r ** 2

    plt.title("V(t)")
    plt.xlabel("t, s")
    plt.ylabel("V, m^3")
    plt.plot(arr_t, arr_v)
    plt.show()