# Bài tập lớn OOP - Nhóm 9

## Giới thiệu
- Yêu cầu: Thu thập dữ liệu về lịch sử
- Nguồn bài tập: https://docs.google.com/document/d/1dI-0LJEQR6v6rokB1idBbUu1vkbUhVn2/mobilebasic?fbclid=IwAR2mZ-Tf3l1tSP9MJOQXibvnqbO6n0gcRIVIpzwRR8yKUq37G231sFxiGAg
- Thành viên nhóm:
    - Nguyễn Thọ Đạt
    - Phạm Trung Hiếu
    - Đặng Hữu Tuấn Minh
    - Nguyễn Doãn Hoàng Thư

## Cài đặt
- Hướng dẫn chạy dự án
    - Trên VSCode: 
        - Tạo 1 file setting.json trong cònig của vscode
        - Copy đoạn code vào file setting.json:
            ```
            {
                "java.project.sourcePaths": ["src"],
                "java.project.outputPath": "bin",
                "java.project.referencedLibraries": [
                "lib/**/*.jar"
                ]
            }
            ```
        - Chạy file "App.java"
    - Các IDE khác:
        - Thêm các thư viện cần thiết trong file lib vào Referenced Libraries
        - Refactor các package nếu cần thiết

## Sử dụng
- Các chức năng chính:
    - Thu thập dữ liệu về lịch sử từ các trang web (wikimedia, nguoikesu,..)
    - Xem các dữ liệu đã thu thập được (Thông qua GUI)
    - Lưu các dữ liệu vào file
    - Xem các dữ liệu được lưu trong file
    - Sắp xếp các dữ liệu (theo tên, a-z)

## Cấu trúc dự án
- crawlertool (Chứa các lớp và giao diện sử dụng phương thức để thu thập dữ liệu)  
- datahandle (Chứa các lớp để xử lý dữ liệu đã thu thập hoặc lưu vào file)   
- entity (Chứa các lớp mô tả các loại dữ liệu thu thập)   
- gui (chứa các package về giao diện người dùng và đồ hoạ)
    - controller (chứa lớp điều khiển giao diện)
    - fxml (chứa các tập giao diện fxml và các lớp tải giao diện fxml)      
    - image (chứa các file ảnh sử dụng trong giao diện)
    - style (chứ các file dịnh nghĩa kiểu cho giao diện)           
- otherstool (chứa các lớp thực hiện các phương thức tĩnh xử lý chương trình)  
- App (main)

## Công nghệ
- SceneBuilder: tạo tạp tin giao diện FXML bằng cách kéo thả
- Javafx: xây dựng giao diện người dùng
- Jsoup: tạo các HTTPRequest để thu thập dữ liệu từ trang web
- Gson và Json: Chuyển các object thành dữ liệu dạng text để lưu trữ
- Multi-Thread Processing: xử lý đa luồng giúp trải nghiệm được liên tục và không bị gián đoạn. Có thể vừa crawl dữ liệu, vừa xem dữ liệu được lưu trong file

## Thuật toán
- Bruce force: tạo liên kết giữa các vật thể bằng cách duyệt qua toàn bộ các dữ liệu đã thu thập được và tạo liên kết 2 chiều thông qua việc so sánh giữa tên và từ khoá liên quan (name - relativeKeyWord)
- Linear Searching: Tìm kiếm tuần tự thông qua toàn bộ dữ liệu mảng, loại bỏ các phần tử không match với từ khoá trong ô tìm kiếm (Tìm kiếm bỏ qua các dấu và kí tự đặc biệt -> độ chính xác tương đối)
- Heap Sort: Sắp xếp dữ liệu bằng heapsort giúp tối ưu thời gian thực thi và bộ nhớ sử dụng



