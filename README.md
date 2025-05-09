### **ỨNG DỤNG QUẢN LÍ THƯ VIỆN (LIBRARY MANAGEMENT SYSTEM)**

**Tác Giả (Authors):**

Group LPN:
1. Hoa Văn Long - 240222390
2. Nguyễn Hải Nam  - 24022414
3. Lê Việt Phú - 24022426

**Miêu Tả (Description):**

- Quản lí thư viện là ứng dụng giúp người dùng quản lí thư viện.
- Ứng dụng được viết bằng Java, thư viện JavaFX, SQL cùng với CSS để tạo nên một giao diện đẹp mắt và dễ dàng sử dụng.
- Ứng dụng sử dụng những quyển sách có sẵn trên Google API để tạo lại mẫu mã sách đa dạng.

**Chức năng chính (Main Function):**

- Quản lý danh mục sách: thêm, sửa, xóa, tìm kiếm sách theo tên, mã ISBN, tác giả...
- Quản lý người dùng: đăng ký, chỉnh sửa thông tin người đọc, phân quyền (quản trị viên, người dùng thông thường).
- Quản lý mượn/trả sách: ghi nhận thời gian mượn, trả, tự động tính hạn mượn.
- Tìm kiếm và lọc dữ liệu nhanh chóng.

**Đối tượng sử dụng (Target User):**

- Nhân viên/thủ thư tại các thư viện trường học, thư viện công cộng hoặc thư viện doanh nghiệp.
- Người đọc, sinh viên, học sinh cần tra cứu và mượn sách.

**UML:**
![Screenshot 2025-05-07 210849](https://github.com/user-attachments/assets/acb31925-c007-4797-98aa-ff2bda0f9bcf)


**Cài Đặt (Download):**
- Sao chép dự án từ kho lưu trữ.
- Mở dự án trong IDE.
- Thay đổi các vùng nhớ cho database.
- Chạy dự án.
- Nếu bạn muốn thay đổi dữ liệu, bạn có thể thay đổi tệp.

**Sử dụng (Usage Instructions):**

_1, Đăng nhập / Đăng ký tài khoản (Login  / Register)._

- Nếu bạn chưa có tài khoản : Nhập Email, mật khẩu (Password) và chọn vai trò (role) của bạn.
        Nhập xong thông tin yêu cầu → ấn Register (Đăng ký) → xác thực Email.

- Nếu bạn đã có tài khoản : Nhập Email, mật khẩu (Password) và chọn vai trò (role) của bạn.
        Nhập xong thông tin yêu cầu → ấn Login (Đăng nhập).

- Nếu bạn đã có tài khoản nhưng quên mật khẩu (Password), bạn có thể đặt lại mật khẩu mới trong “Forgot password?”.

_2, Trang chủ (Home)._
- Khi bạn đăng nhập / đăng ký thành công, bạn sẽ được chuyển đến trang chủ của phần mềm.
Trang chủ được có sự khác biệt giữa vai trò (role) của bạn trong hệ thống.

- Nút Home (Home button) : chuyển về giao diện Home. Giao diện Home hiện các sách được đánh giá cao (Top - Rated Books) và các sách đang xu hướng (Trending Books).

- Nút Books (Books button) :  Tra sách (Search) và mượn sách (Borrow), bạn có thể đánh giá (rate)  sách đã mượn. Mỗi sách đều hiện miêu tả (Description) và các nhận xét (Comments) của người khác. Giao diện Books còn hiện danh sách các sách bạn đã mượn và hạn mượn sách (DueDate).

- Nút Chat (Chat button) : Nơi bạn có thể trao đổi, trò chuyện với những người khác.

- Riêng giao diện Manager sẽ có thêm nút Readers (Readers button) : Nơi Manager quản lí người dùng, quản lí các sách mà người dùng đã mượn (Borrow) hoặc xóa tài khoản người dùng (Delete), cũng như thêm tài khoản mới (Insert Reader).

- Nút Settings (Settings button) : Hiện thêm 3 nút : nút Pause Music (Pause Music button) - nơi bạn tắt nhạc của phần mềm, nút Account (Account button) - nơi bạn cập nhật thông tin cá nhân, và nút Logout (Logout button) - nơi bạn đăng xuất khỏi phần mềm.

**Demo**

![Screenshot 2025-05-07 211902](https://github.com/user-attachments/assets/c175263a-9358-4893-9eee-1e9435ae5997)

![image](https://github.com/user-attachments/assets/0c7c762d-25bb-4245-8d16-9333ec421e10)

![Screenshot 2025-05-07 212000](https://github.com/user-attachments/assets/583c1f86-287f-4cf3-990f-fbb8745f6e92)

![image](https://github.com/user-attachments/assets/3e11802b-aa14-4289-aff0-fb77144d3868)

![Screenshot 2025-05-07 211931](https://github.com/user-attachments/assets/ac914542-5b12-4c5e-b712-82a16a788df2)

![image](https://github.com/user-attachments/assets/cfe1dca7-1764-4d2e-9bf6-fc18606b9dfa)

![Screenshot 2025-05-07 212337](https://github.com/user-attachments/assets/49e83766-48e6-4d95-953a-57b71d668cea)


**Cải tiến trong tương lai (Future Improvements):**
- Cải tiến chức năng đăng nhập. 
- Thêm các trò chơi để giải trí. 
- Cải thiện giao diện người dùng. 


**Trạng thái dự án (Project's progress):**
- Dự án chưa hoàn thành (80%).
