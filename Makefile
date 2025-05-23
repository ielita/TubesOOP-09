# Variables
SRC_DIR=src
BIN_DIR=bin
CLASSES=$(BIN_DIR)/Main.class
MAIN_CLASS=main.Main

# Default rule: compile all and run
run: $(CLASSES)
	java -cp $(BIN_DIR) $(MAIN_CLASS)

# Compile all .java files in src
$(CLASSES):
	if not exist $(BIN_DIR) mkdir $(BIN_DIR)
	javac -d $(BIN_DIR)  $(SRC_DIR)/entity/*.java $(SRC_DIR)/items/*.java $(SRC_DIR)/main/*.java  $(SRC_DIR)/object/*.java $(SRC_DIR)/tile/*.java

# Clean compiled files
clean:
	if exist $(BIN_DIR) rmdir /s /q $(BIN_DIR)
