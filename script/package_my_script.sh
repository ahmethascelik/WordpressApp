#!/bin/bash
"$HOME"/Library/Python/3.8/bin/pyinstaller --onefile editor.py
mv dist/editor .
rm -R dist
rm editor.spec
rm -rf build
