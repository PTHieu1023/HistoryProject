# Bài tập lớn OOP - Nhóm 9

## Giới thiệu
Đây là dự án bài tập lớn học phần OOP của nhóm 9 - 139411 (2022.2 - HUST)

Thành viên:
- Nguyễn Thọ Đạt
- Phạm Trung Hiếu
- Đặng Hữu Tuấn Minh
- Nguyễn Doãn Hoàng Thư

## Mô tả
Có rất nhiều các trang web cung cấp thông tin về lịch sử Việt Nam (https://nguoikesu.com/, Wikipedia, DBPedia, …). Cần tìm các trang web này và thu thập tự động dữ liệu về lịch sử Việt Nam và liên kết các dữ liệu này lại với nhau. 

Các thực thể cần thu thập bao gồm:
- Các triều đại lịch sử Việt Nam (thời Tiền Sử, Hồng Bàng, An Dương Vương, Bắc Thuộc lần I, …)
- Các nhân vật lịch sử Việt Nam (Ví dụ, các vị vua Việt Nam có thể thu thập tại https://vi.wikipedia.org/wiki/Vua_Vi%E1%BB%87t_Nam)
- Các địa điểm du lịch (chùa, đình, miếu, gò, …) và các di tích lịch sử Việt Nam
- Các lễ hội văn hóa Việt Nam
- Các sự kiện lịch sử Việt Nam

Mỗi thực thể cần có định danh, có các thuộc tính, và quan trọng, các thực thể cần được liên kết với nhau. Một số ví dụ:
- Lễ hội đền Hùng:
    - Địa điểm: Tổ chức ở TP Việt Trì, tỉnh Phú Thọ
    - Ngày tổ chức: 10/3 âm lịch
    - Nhân vật lịch sử liên quan: Tưởng nhớ Vua Hùng
    - Sự kiện liên quan : vua Hùng dựng nước
    - Di tích liên quan: Đền Hùng…

- Nhân vật lịch sử Vua Hùng:
    - Cha: Lạc Long Quân
    - Lên ngôi: năm 2524 trước công nguyên
    - Năm sinh: không rõ
    - Năm mất: không rõ…

Lưu ý:
- Việc thu thập dữ liệu phải là tự động
- Phải thống nhất cách đặt tên các thuộc tính cho mỗi loại thực thể

Yêu cầu cần đạt:
- Số lượng thực thể thu thập cho mỗi loại phải đa dạng phong phú (cần thu thập từ nhiều nguồn). Nếu thu thập được số lượng ít, kết quả của nhóm sẽ bị đánh giá thấp.
- Đảm bảo độ chính xác
- Cần xử lý hợp nhất dữ liệu từ nhiều nguồn
    - VD1: nguồn X thu thập được 1200 nhân vật lịch sử, nguồn Y thu thập được 1500 nhân vật lịch sử. Cần xử lý lấy hợp lại thành 1700 nhân vật lịch sử (có những nhân vật lịch sử có ở nguồn này, nhưng lại không có ở nguồn kia, và có những nhân vật lịch sử có ở cả 2 nguồn)
    - VD2: Nguồn X và Y cùng thu thập được dữ liệu về nhân vật lịch sử H. Nguồn X tìm được 10 thông tin, nguồn Y tìm được 15 thông tin về nhân vật lịch sử H. Khi đó cần xử lý hợp lại thành 18 thông tin cho nhân vật lịch sử H. Nếu có trường thông tin bị xung đột, cần tổ chức dữ liệu sao cho lưu lại thông tin rõ ràng. VD: H sinh năm 1230 theo nguồn X, H sinh năm 1231 theo nguồn Y, và H sinh năm 1232 theo nguồn Z.
- Dữ liệu thu thập được cần được lưu trữ dưới dạng JSON hoặc CSV. Sau đó cung cấp chức năng tìm kiếm và hiển thị thông tin cho người dùng.



## Yêu cầu
- Tất cả cần nộp cùng 1 thời điểm: upload lên thư mục GG Drive + bản cứng báo cáo. Khi bảo vệ, các nhóm download xuống
- Sản phẩm cần nộp (nộp vào tuần …):
- Báo cáo
- Slide trình bày
- Video demo
- Mã nguồn chương trình
- Các file tài nguyên (Cần chạy chương trình ở nhà trước, để lấy được đủ dữ liệu)
- Nộp báo cáo bản cứng

- Báo cáo:
    - Phân công công việc các thành viên trong nhóm, % đóng góp (con số lượng hóa cụ thể) của từng thành viên
    - Con số thống kê về dữ liệu thu thập được. Ví dụ
        - Bao nhiêu nhân vật lịch sử, bao nhiêu lễ hội, bao nhiêu di tích, …
        - Bao nhiêu thuộc tính dữ liệu cho mỗi loại thực thể
        - Bao nhiêu liên kết giữa các thực thể
        - Lấy dữ liệu của bao nhiêu nguồn, là những nguồn nào. Mỗi nguồn lấy được dữ liệu gì
    - Vẽ biểu đồ UML, Biểu đồ phụ thuộc gói, Biểu đồ lớp
    - Giải thích thiết kế, các gói (package) dùng để làm gì, các lớp ý tưởng là gì
    - Giải thích các kỹ thuật lập trình hướng đối tượng đã áp dụng
    - Nhóm đã áp dụng kỹ thuật gì, ở đâu, lợi ích đem lại là gì
    - Liệt kê công nghệ sử dụng, thuật toán hay ho (nếu có)
    - Hướng dẫn sử dụng ngắn gọn + 1 số ảnh quan trọng demo chương trình

## Cài đặt
- Hướng dẫn chạy dự án
    - Trên VSCode: 
        - Tạo 1 file setting.json trong config của vscode
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





