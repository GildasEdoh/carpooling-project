import os

def categorize_fonts(fonts):
    categories = {
        'italic': [],
        'light': [],
        'bold': [],
        'medium': [],
        'regular': [],
        'thin': [],
        'semibold': [],
        'extrabold': [],
        'black': []
    }

    for font in fonts:
        font_name = os.path.splitext(font)[0].lower()
        if 'italic' in font_name:
            categories['italic'].append(font_name)
        if 'light' in font_name:
            categories['light'].append(font_name)
        if 'bold' in font_name and 'extrabold' not in font_name and 'semibold' not in font_name:
            categories['bold'].append(font_name)
        if 'medium' in font_name:
            categories['medium'].append(font_name)
        if 'regular' in font_name:
            categories['regular'].append(font_name)
        if 'thin' in font_name:
            categories['thin'].append(font_name)
        if 'semibold' in font_name:
            categories['semibold'].append(font_name)
        if 'extrabold' in font_name:
            categories['extrabold'].append(font_name)
        if 'black' in font_name:
            categories['black'].append(font_name)

    return categories

def generate_font_xml(directory, output_file):
    fonts = [f for f in os.listdir(directory) if f.endswith(('.ttf', '.otf', '.woff', '.woff2'))]
    categories = categorize_fonts(fonts)

    with open(output_file, 'w', encoding='utf-8') as file:
        file.write('<?xml version="1.0" encoding="utf-8"?>\n')
        file.write('<font-family xmlns:android="http://schemas.android.com/apk/res/android">\n')

        for category, font_list in categories.items():
            for font in font_list:
                file.write(f'  <font\n')
                file.write(f'    android:fontStyle="normal"\n')
                file.write(f'    android:fontWeight="400"\n')
                file.write(f'    android:font="@font/{font}" />\n')

        file.write('</font-family>\n')

if __name__ == "__main__":
    current_directory = os.getcwd()
    output_file = os.path.join(current_directory, 'poppins.xml')
    generate_font_xml(current_directory, output_file)