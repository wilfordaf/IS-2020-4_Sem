import matplotlib.pyplot as plt
import numpy as np

from math import factorial, sqrt
from scipy.misc import derivative


def f(x, l):
    return (x ** 2 - 1) ** l


def der_f(x, l, m):
    order = l + abs(m)
    return derivative(f, x0=x, n=order, args=[l], order=2 * order + 1)


def calculate_func(theta, phi, l, m):
    a = sqrt(factorial(l - abs(m)) * (2 * l + 1) / (factorial(l + abs(m)) * 4 * np.pi))
    factor = 1 / 2 ** l * 1 / factorial(l)
    result = (a * np.cos(phi * m) * factor * (1 - np.cos(theta)**2)**(abs(m) / 2) * der_f(np.cos(theta), l, m))**2
    return result


def draw_axis(fig, y_lim: list):
    rect = [0.1, 0.1, 0.8, 0.8]

    ax_linear = fig.add_axes(rect)
    ax_linear.axes.get_xaxis().set_visible(False)
    ax_linear.spines["right"].set_visible(False)
    ax_linear.spines["top"].set_visible(False)
    ax_linear.spines["bottom"].set_visible(False)
    ax_linear.set_ylim(y_lim)

    ax_polar = fig.add_axes(rect, polar=True, frameon=False)
    ax_polar.set_theta_zero_location("N")
    ax_polar.set_xticks([i / 10000 for i in range(0, 2 * 31415 + 1, 5236)])
    return ax_polar


def draw_tick_circles(polar_subplot, max_r):
    colors = ["green", "blue"]
    circle_amount = 1
    while max_r / circle_amount > 0.05:
        circle_amount += 1

    for i in range(circle_amount):
        polar_subplot.plot(theta,
                           [(i + 1) * max_r / circle_amount for _ in range(len(theta))],
                           color=colors[i % 2],
                           linewidth=0.5)


def draw_func_plot(theta, r, polar_subplot):
    polar_subplot.plot(theta, r, color="black", linewidth=0.5)


def draw_func_plot_3d(fig, theta, phi, r):
    ax = fig.add_subplot(1, 1, 1, projection='3d')

    func_x = r * np.sin(phi) * np.cos(theta)
    func_y = r * np.sin(phi) * np.sin(theta)
    func_z = r * np.cos(phi)

    plot_func = ax.plot_wireframe(func_x, func_y, func_z, color='blue')\

    r_sphere = np.empty((len(theta), len(phi)))
    a = np.max(r)
    r_sphere.fill(a)
    x_sphere = r_sphere * np.sin(phi) * np.cos(theta)
    y_sphere = r_sphere * np.sin(phi) * np.sin(theta)
    z_sphere = r_sphere * np.cos(phi)

    plot_sphere = ax.contour3D(x_sphere, y_sphere, z_sphere, 20, cmap='binary')


if __name__ == '__main__':
    fig_3d = plt.figure()
    fig_2d = plt.figure()

    np.set_printoptions(threshold=np.inf, linewidth=np.inf)
    theta, phi = np.linspace(0, 2 * np.pi, 360), np.linspace(0, 2 * np.pi, 360)
    tuple_phi, tuple_theta = np.meshgrid(theta, phi)

    r = calculate_func(tuple_theta, tuple_phi, 10, -8)
    max_r = np.max(r)

    polar_subplot = draw_axis(fig_2d, [-max_r, max_r])

    draw_tick_circles(polar_subplot, max_r)
    draw_func_plot(theta, np.amax(r, axis=1), polar_subplot)
    draw_func_plot_3d(fig_3d, tuple_theta, tuple_phi, r)

    plt.yticks([])
    plt.show()
