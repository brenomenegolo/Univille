a = 4
b = 10
dias = 0

while a < b:
    a = a + (a * 0.03)
    b = b + (b * 0.015)
    dias += 1

print("Dias necessários:", dias)