with open("ingredients.sql", "w") as f:
    f.write("INSERT INTO Ingredient (name) VALUES\n")
    for i in range(1, 1001):
        line = f"('Ingredient_{i}')"
        if i < 1000:
            line += ","
        line += "\n"
        f.write(line)
