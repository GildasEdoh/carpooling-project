import os

def rename_fonts(directory):
    for filename in os.listdir(directory):
        if filename.endswith(('.ttf', '.otf', '.woff', '.woff2')):
            new_name = filename.lower().replace('-', '_')
            old_file = os.path.join(directory, filename)
            new_file = os.path.join(directory, new_name)
            
            if old_file != new_file:  # Éviter de renommer si le nom est déjà correct
                try:
                    os.rename(old_file, new_file)
                    print(f'Renamed: {filename} -> {new_name}')
                except FileExistsError:
                    print(f"Error: The file {new_name} already exists.")
                except Exception as e:
                    print(f"Error renaming {filename}: {e}")

if __name__ == "__main__":
    current_directory = os.getcwd()
    rename_fonts(current_directory)
