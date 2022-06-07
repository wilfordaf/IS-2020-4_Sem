from lab3.entities.scrMatrix import SCRMatrix


class LUDecomposition:
    def __init__(self, matrix: SCRMatrix):
        self.matrix = matrix
        self.L = SCRMatrix()
        self.U = SCRMatrix()

    def decompose(self) -> (SCRMatrix, SCRMatrix):
        n = self.matrix.get_n()
        for i in range(n):
            self.L.ind_ptr.append(self.L.ind_ptr[-1])
            self.U.ind_ptr.append(self.U.ind_ptr[-1])
            for j in range(n):
                if i == j:
                    LUDecomposition.__append(self.L, j, 1)
                    Uij = self.__calculateUij(i, j)
                    LUDecomposition.__append_if_not_zero(self.U, j, Uij)
                elif i < j:
                    Uij = self.__calculateUij(i, j)
                    LUDecomposition.__append_if_not_zero(self.U, j, Uij)
                else:
                    Lij = self.__calculateLij(i, j)
                    LUDecomposition.__append_if_not_zero(self.L, j, Lij)

        return self.L, self.U

    def __calculateUij(self, i: int, j: int):
        elements_sum = 0
        for k in range(i):
            elements_sum += self.L.get(i, k) * self.U.get(k, j)

        return self.matrix.get(i, j) - elements_sum

    def __calculateLij(self, i: int, j: int):
        elements_sum = 0
        for k in range(j):
            elements_sum += self.L.get(i, k) * self.U.get(k, j)

        return (self.matrix.get(i, j) - elements_sum) / self.U.get(j, j)

    @staticmethod
    def __append_if_not_zero(m: SCRMatrix, index: int, value: int):
        if value != 0:
            LUDecomposition.__append(m, index, value)

    @staticmethod
    def __append(m: SCRMatrix, index: int, value: int):
        m.data.append(value)
        m.ind.append(index)
        m.ind_ptr[-1] += 1
