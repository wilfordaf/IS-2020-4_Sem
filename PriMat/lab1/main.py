from solution import Solution
import matplotlib.pyplot as plt

if __name__ == "__main__":
    solution = Solution(0.00001, 1, 0.00001)

    plt.figure(figsize=(16, 9))
    plt.title("Interval range (iteration)")

    solution.bisectionMethod()
    solution.goldenRatioMethod()
    solution.fibonacciMethod()
    solution.parablesMethod()
    solution.brentMethod()

    plt.legend()
    plt.show()
