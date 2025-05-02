# Variabel
SRC_DIR=src/main
BIN_DIR=bin
CLASSES=$(BIN_DIR)/Main.class
MAIN_CLASS=main.Main

# Aturan default: compile semua dan jalankan
run: $(CLASSES)
	java -cp $(BIN_DIR) $(MAIN_CLASS)

# Compile semua file .java di src/main
$(CLASSES):
	if not exist $(BIN_DIR) mkdir $(BIN_DIR)
	javac -d $(BIN_DIR) $(SRC_DIR)/*.java

# Bersihkan file hasil kompilasi (Windows version)
clean:
	if exist $(BIN_DIR) rmdir /s /q $(BIN_DIR)
