APP_NAME:=SlowArc

# Define source and build directories
SRC_DIR := src
BUILD_DIR := build

# Find all Java source files in the source directory
JAVA_FILES := $(wildcard $(SRC_DIR)/*.java)
CLASS_FILES := $(patsubst $(SRC_DIR)/%.java,$(BUILD_DIR)/%.class,$(JAVA_FILES))


# Main target: create the jar file
all: 
	mkdir -p $(BUILD_DIR)
	javac -g -d $(BUILD_DIR) $(JAVA_FILES)
	jar cfm $(APP_NAME).jar $(SRC_DIR)/MANIFEST.MF -C $(BUILD_DIR)/ .
# Clean up compiled files
clean:
	rm -rf $(BUILD_DIR) $(APP_NAME).jar

# Phony targets to avoid conflicts with file names
.PHONY: all clean
