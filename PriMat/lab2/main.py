from solution import Solution, Point
import matplotlib.pyplot as plt
from mpl_toolkits import mplot3d
import numpy as np


def f(x, y):
    return x ** 2 + 5 * y ** 2


def draw_graph(func_result, color, name):
    dots = func_result[0]
    print(f"{name.upper()} minimum found in {dots[-1]} with {func_result[1]} func calculations")


def draw_graph(func_result, color, func_name):
    dots = func_result[0]
    print(f"{func_name} minimum found in {dots[-1]} with {func_result[1]} func calculations")

    xline = [i.x for i in dots]
    yline = [i.y for i in dots]
    zline = []
    for i in range(len(xline)):
        zline.append(solution.__func__([xline[i], yline[i]]))

    ax.plot3D(xline, yline, zline, color, label=func_name)

    ax.plot3D(xline, yline, zline, color, label=func_name)


if __name__ == "__main__":
    x = np.linspace(-10, 10, 1000)
    y = np.linspace(-10, 10, 1000)
    X, Y = np.meshgrid(x, y)
    Z = f(X, Y)

    fig = plt.figure()
    ax = plt.axes(projection="3d")
    ax.contour3D(X, Y, Z, cmap="binary")

    start_point = Point(-4, 5)
    solution = Solution(start_point, 10 ** -6)

    constant_learning_rate = solution.constant_learning_rate_method(0.2)
    draw_graph(constant_learning_rate, "red", "constant_learning_rate")

    step_fragmentation_method = solution.step_fragmentation_method(0.1, 0.95, 0.05)

    start_point = Point(2, 3)
    solution = Solution(start_point, 10 ** -6)

    constant_learning_rate = solution.constant_learning_rate_method(0.1)
    draw_graph(constant_learning_rate, "red", "constant_learning_rate")

    step_fragmentation_method = solution.step_fragmentation_method(0.1, 0.95, 0.2)

    draw_graph(step_fragmentation_method, "blue", "step_fragmentation_method")

    golden_ratio_method = solution.golden_ratio_method()
    draw_graph(golden_ratio_method, "green", "golden_ratio_method")

    fibonacci_method = solution.fibonacci_method()
    draw_graph(fibonacci_method, "purple", "fibonacci_method")

    fletcher_reeves_method = solution.fletcher_reeves_method()
    draw_graph(fletcher_reeves_method, "orange", "fletcher_reeves_method")

    ax.view_init(25, 25)
    plt.legend()
    plt.show()
