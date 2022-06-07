from lab3.entities.gilbertMatrix import *
from algorithms.jacobiRotationMethod import *
import matplotlib.pyplot as plt
import threading



def test_matrix_functions_and_eigenvalues():
    print("Square symmetrical matrix")
    matrix = squareMatrix.SquareMatrix.fill_symmetrical_da(3)
    matrix.print()

    print("\nSquare symmetrical matrix")
    gilbert_matrix = GilbertMatrix(3).fill()
    gilbert_matrix.print()

    print("\nJacobi method")
    values, vectors, iteration_count = JacobiRotationMethod(gilbert_matrix.to_SCRMatrix(), 10 ** -3).solve()

    print("\nEigenvalues")
    print(values)
    print("\nEigenvectors")
    vectors.to_SquareMatrix().print()
    print("\nIteration amount")
    print(iteration_count)


def test_system_solutions_on_diagonal_matrices():
    N = [i * 10 for i in range(1, 9)]
    iter_amount = []

    for n in N:
        matrix = SquareMatrix.fill_symmetrical_da(n).to_SCRMatrix()
        values, vectors, iteration_count = JacobiRotationMethod(matrix, 10 ** -3).solve()
        print(f"\nN = {n}")
        print(values)
        vectors.to_SquareMatrix().print()
        iter_amount.append(iteration_count)

    plt.plot(N, iter_amount, color="orange")
    plt.xlabel("N")
    plt.ylabel("iterations")
    plt.show()


def test_system_solutions_on_gilbert_matrices():
    N = [i * 5 for i in range(1, 11)]
    iter_amount = []

    for n in N:
        matrix = GilbertMatrix(n).fill().to_SCRMatrix()
        values, vectors, iteration_count = JacobiRotationMethod(matrix, 10 ** -6).solve()
        print(f"\nN = {n}")
        print(values)
        vectors.to_SquareMatrix().print()
        iter_amount.append(iteration_count)

    plt.plot(N, iter_amount, color="orange")
    plt.xlabel("N")
    plt.ylabel("iterations")
    plt.show()


if __name__ == "__main__":
    # test_matrix_functions_and_eigenvalues()
    # thread1 = threading.Thread(target=test_system_solutions_on_diagonal_matrices())
    # thread1.start()
    thread2 = threading.Thread(target=test_system_solutions_on_gilbert_matrices())
    thread2.start()

