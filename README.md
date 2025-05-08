**ỨNG DỤNG QUẢN LÍ THƯ VIỆN** 

**Tác Giả**

Group LPN:

1. Hoa Văn Long - 240222390
2. Nguyễn Hải Nam  - 24022414
3. Lê Việt Phú - 24022426

**Miêu Tả :**

Quản lí thư viện là ứng dụng giúp người dùng quản lí thư viện . Ứng dụng được viết bằng Java , Thư viện JavaFX , SQL cùng với Css để tạo lên một giao diện đẹp mắt và dễ dàng sử dụng . Ứng dụng sử dụng những quyển sách có sẵn trên Google API để tạo lại mẫu mã sách đa dạng.

**Chức năng chính**:

- Quản lý danh mục sách: thêm, sửa, xóa, tìm kiếm sách theo tên, mã ISBN, tác giả...
- Quản lý người dùng: đăng ký, chỉnh sửa thông tin người đọc, phân quyền (quản trị viên, người dùng thông thường).
- Quản lý mượn/trả sách: ghi nhận thời gian mượn, trả, tự động tính hạn mượn.
- Tìm kiếm và lọc dữ liệu nhanh chóng.

**Đối tượng sử dụng**:

- Nhân viên/thủ thư tại các thư viện trường học, thư viện công cộng hoặc thư viện doanh nghiệp.
- Người đọc, sinh viên, học sinh cần tra cứu và mượn sách.

**UML :**
![Screenshot 2025-05-07 210849](https://github.com/user-attachments/assets/acb31925-c007-4797-98aa-ff2bda0f9bcf)


Cài Đặt
1. Sao chép dự án từ kho lưu trữ.
2. Mở dự án trong IDE.
3. Thay đổi các vùng nhớ cho database
3. Chạy dự án.
4. Nếu bạn muốn thay đổi dữ liệu, bạn có thể thay đổi tệp E_V.txt và V_E.txt.

**Sử dụng**

Đăng nhập / Đăng ký tài khoản (Login  / Register).

Nếu bạn chưa có tài khoản : Nhập Email, mật khẩu (Password) và chọn vai trò (role) của bạn.
        Nhập xong thông tin yêu cầu → ấn Register (Đăng ký) → xác thực Email.

Nếu bạn đã có tài khoản : Nhập Email, mật khẩu (Password) và chọn vai trò (role) của bạn.
        Nhập xong thông tin yêu cầu → ấn Login (Đăng nhập).

Nếu bạn đã có tài khoản nhưng quên mật khẩu (Password), bạn có thể đặt lại mật khẩu mới trong “Forgot password?”.

Trang chủ (Home).
Khi bạn đăng nhập / đăng ký thành công, bạn sẽ được chuyển đến trang chủ của phần mềm.
Trang chủ được có sự khác biệt giữa vai trò (role) của bạn trong hệ thống.

Nút Home (Home button) : chuyển về giao diện Home. Giao diện Home hiện các sách được đánh giá cao (Top - Rated Books) và các sách đang xu hướng (Trending Books).

Nút Books (Books button) :  Tra sách (Search) và mượn sách (Borrow), bạn có thể đánh giá (rate)  sách đã mượn. Mỗi sách đều hiện miêu tả (Description) và các nhận xét (Comments) của người khác. Giao diện Books còn hiện danh sách các sách bạn đã mượn và hạn mượn sách (DueDate).

Nút Chat (Chat button) : Nơi bạn có thể trao đổi, trò chuyện với những người khác.

Riêng giao diện Manager sẽ có thêm nút Readers (Readers button) : Nơi Manager quản lí người dùng, quản lí các sách mà người dùng đã mượn (Borrow) hoặc xóa tài khoản người dùng (Delete).

Nút Settings (Settings button) : Hiện thêm 2 nút : nút Account (Account button) - nơi bạn cập nhật thông tin cá nhân, và nút Logout (Logout button) - nơi bạn đăng xuất khỏi phần mềm.

**Demo**


![Screenshot 2025-05-07 211902](https://github.com/user-attachments/assets/c175263a-9358-4893-9eee-1e9435ae5997)
![Screenshot 2025-05-07 212034](https://github.com/user-attachments/assets/22e3d8ad-5520-4218-95f9-76a94af1f2a2)
![Screenshot 2025-05-07 212000](https://github.com/user-attachments/assets/583c1f86-287f-4cf3-990f-fbb8745f6e92)
![Screenshot 2025-05-07 211944](https://github.com/user-attachments/assets/5236e54c-5581-435a-bd66-52e50af2f68f)
![Screenshot 2025-05-07 211931](https://github.com/user-attachments/assets/ac914542-5b12-4c5e-b712-82a16a788df2)
![Screenshot 2025-05-07 212326](https://github.com/user-attachments/assets/f7c505ab-bd5f-4fee-bcb2-3b02e6c5d220)
![Screenshot 2025-05-07 212337](https://github.com/user-attachments/assets/49e83766-48e6-4d95-953a-57b71d668cea)

**Cải tiến trong tương lai**

1. Cải tiến chức năng đăng nhập.
2. Thêm các trò chơi để giải trí 
3. Cải thiện giao diện người dùng 

Trạng thái dự án 

Dự án chưa hoàn thành(80%)
