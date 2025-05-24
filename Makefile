# Compiler and flags
JC = javac
JAVA = java
JFLAGS = -d "$(BIN_DIR)" --release 23

# Directories
BIN_DIR = bin
SRC_DIR = src
RES_DIR = res

# Main class
MAIN_CLASS = main.Main

# Default target
all: clean compile run

# Create bin directory
$(BIN_DIR):
	if not exist "$(BIN_DIR)" mkdir "$(BIN_DIR)"

# Clean build files
clean:
	if exist "$(BIN_DIR)" rmdir /s /q "$(BIN_DIR)"
	if exist "sources.txt" del "sources.txt"

# Compile
compile: $(BIN_DIR)
	@echo Creating source list...
	@dir /s /b "$(SRC_DIR)\*.java" > sources.txt
	@echo Compiling Java files...
	@"$(JC)" $(JFLAGS) @sources.txt
	@del sources.txt

# Run the game
run: compile resources
	@"$(JAVA)" -cp "$(BIN_DIR)" $(MAIN_CLASS)

# Copy resources
resources: $(BIN_DIR)
	@if exist "$(RES_DIR)" xcopy /E /I /Y "$(RES_DIR)" "$(BIN_DIR)\res"

.PHONY: all clean compile run resources