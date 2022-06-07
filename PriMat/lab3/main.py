import random

from lab3.algorithms.jacobiSystemSolver import JacobiSystemSolver
from lab3.algorithms.seidelSystemSolver import SeidelSystemSolver
from lab3.entities.squareMatrix import SquareMatrix
from lab3.entities.gilbertMatrix import GilbertMatrix
from lab3.algorithms.inverseFromLU import InverseFromLU
from lab3.algorithms.luDecomposition import LUDecomposition
from lab3.algorithms.gaussSystemSolver import GaussSystemSolver

from time import time
import matplotlib.pyplot as plt
import threading


def test_matrix_functions_and_system_solutions():
    matrix = SquareMatrix([
        [10, 0, 5, 0, 3],
        [4, 50, 0, 9, 2],
        [1, 0, 70, 2, 0],
        [0, 0, 3, 40, 0],
        [3, 4, 0, 2, 90]
    ])

    print("SCR Matrix")
    scr_matrix = matrix.to_SCRMatrix()
    scr_matrix.print()

    print("\nSquare Matrix")
    square_matrix = scr_matrix.to_SquareMatrix()
    square_matrix.print()

    print("\nLU Decomposition")
    l, u = LUDecomposition(scr_matrix).decompose()

    print("\nL Matrix")
    l.to_SquareMatrix().print()

    print("\nU Matrix")
    u.to_SquareMatrix().print()

    print("\nL * U = A")
    (l.to_SquareMatrix() * u.to_SquareMatrix()).print()

    print("\nSystem solution via Gauss Method")
    x_gauss = GaussSystemSolver(scr_matrix, [9, 1, 4, 3, 5]).solve()
    print(x_gauss)

    print("\nSystem solution via Jacobi Method")
    x_jacobi = JacobiSystemSolver(scr_matrix, [9, 1, 4, 3, 5], 10 ** (-6)).solve()
    print(x_jacobi)

    print("\nSystem solution via Seidel Method")
    x_seidel = SeidelSystemSolver(scr_matrix, [9, 1, 4, 3, 5], 10 ** (-6)).solve()
    print(x_seidel)

    print("\nInverse matrix")
    inverse_matrix = InverseFromLU(scr_matrix).calculate()
    inverse_matrix.to_SquareMatrix().print()

    print("\nA * A^(-1)")
    (matrix * inverse_matrix.to_SquareMatrix()).print()

    print("\nGilbert Matrix")
    gilbert_matrix = GilbertMatrix(5).fill()
    gilbert_matrix.print()

    print("\nDiagonal Matrix")
    diagonal_matrix = SquareMatrix.fill_diagonal_advantage(5)
    diagonal_matrix.print()


def test_system_solutions_on_diagonal_matrices():
    N = [50 * i for i in range(1, 11)]

    t_gauss = []
    t_jacobi = []

    for n in N:
        matrix = SquareMatrix.fill_diagonal_advantage(n).to_SCRMatrix()
        F = [random.randint(-n // 2, n // 2) for _ in range(n)]

        start_time = time()
        x_gauss = GaussSystemSolver(matrix, F).solve()
        t_gauss.append(time() - start_time)

        start_time = time()
        x_jacobi = JacobiSystemSolver(matrix, F, 10 ** (-3)).solve()
        t_jacobi.append(time() - start_time)

        errors = []
        for gauss, jacobi in zip(x_gauss, x_jacobi):
            errors.append(abs(1 - jacobi / gauss))

        print(f"Average error is {sum(errors) / n}")

    plt.plot(N, t_gauss, color="orange")
    plt.plot(N, t_jacobi, color="blue")
    plt.legend(["Gauss Method", "Jacobi Method"])
    plt.xlabel("N")
    plt.ylabel("t, c")
    plt.show()


def test_system_solutions_on_gilbert_matrices():
    N = [i for i in range(2, 7)]

    t_gauss = []
    t_seidel = []

    for n in N:
        matrix = GilbertMatrix(n).fill().to_SCRMatrix()
        F = [random.randint(-100, 100) / 100 for _ in range(n)]

        start_time = time()
        x_gauss = GaussSystemSolver(matrix, F).solve()
        t_gauss.append(time() - start_time)

        start_time = time()
        x_seidel = SeidelSystemSolver(matrix, F, 10**(-1)).solve()
        t_seidel.append(time() - start_time)

        errors = []
        for gauss, seidel in zip(x_gauss, x_seidel):
            errors.append(abs(1 - seidel / gauss))

        print(f"Average error is {(sum(errors) / n)}")

    plt.plot(N, t_gauss, color="orange")
    plt.plot(N, t_seidel, color="purple")
    plt.legend(["Gauss Method", "Seidel Method"])
    plt.xlabel("N")
    plt.ylabel("t, c")
    plt.show()


if __name__ == "__main__":
    test_matrix_functions_and_system_solutions()
    # thread1 = threading.Thread(target=test_system_solutions_on_diagonal_matrices())
    # thread1.start()
    # thread2 = threading.Thread(target=test_system_solutions_on_gilbert_matrices())
    # thread2.start()

