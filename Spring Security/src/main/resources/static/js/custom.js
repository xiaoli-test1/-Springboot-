// custom.js

// 自动调整 iframe 高度
function SetIFrameHeight() {
    const iframe = document.getElementById('iframe');
    if (iframe.contentWindow) {
        iframe.style.height = iframe.contentWindow.document.body.scrollHeight + 'px';
    }
}

// 为阅读和操作按钮添加事件监听
document.addEventListener('DOMContentLoaded', () => {
    // 为所有"阅读"按钮添加事件监听
    const readButtons = document.querySelectorAll('.btn.bg-olive');
    readButtons.forEach(button => {
        button.addEventListener('click', function() {
            highlightButton(this); // 高亮按钮
            const bookTitle = this.closest('tr').querySelector('td:nth-child(1)').textContent; // 获取书名
            openBookReadingPage(bookTitle); // 打开阅读页面
        });
    });

    // 鼠标悬停到表格行时高亮行
    const tableRows = document.querySelectorAll('#datalist tbody tr');
    tableRows.forEach(row => {  row.addEventListener('mouseenter', () => {
        row.style.backgroundColor = '#f0f8ff'; // 悬停时高亮行
    });
        row.addEventListener('mouseleave', () => {
            row.style.backgroundColor = ''; // 恢复原来的行背景颜色
        });
    });
});

// 动态高亮被点击的按钮
function highlightButton(button) {
    button.style.backgroundColor = '#5cb85c'; // 点击后的背景色
    button.style.color = '#fff'; // 点击后的文字颜色
    setTimeout(() => {
        button.style.backgroundColor = ''; // 短暂高亮后恢复原样
        button.style.color = '';
    }, 300); // 300毫秒后恢复样式
}

// 打开书籍阅读页面的函数
function openBookReadingPage(bookTitle) {
    const iframe = document.getElementById('iframe');
    switch (bookTitle) {
        case 'Spring Cloud 微服务架构开发':
            iframe.src = '/book/read/spring-cloud'; // 假设有相应的阅读页面
            break;
        case 'Spring Boot企业级开发教程':
            iframe.src = '/book/read/spring-boot'; // 假设有相应的阅读页面
            break;
        default:
            alert('没有找到该书籍的阅读页面！'); // 提示用户
    }
}

// 监听页面大小变化以调整 iframe
window.addEventListener('resize', SetIFrameHeight);

// 图书列表页功能
document.addEventListener('DOMContentLoaded', function() {
    // 搜索功能
    const searchInput = document.querySelector('.search-box input');
    if (searchInput) {
        searchInput.addEventListener('input', function() {
            const searchTerm = this.value.toLowerCase();
            const bookCards = document.querySelectorAll('.book-card');
            
            bookCards.forEach(card => {
                const title = card.querySelector('.book-title').textContent.toLowerCase();
                const author = card.querySelector('.book-author').textContent.toLowerCase();
                const publisher = card.querySelector('.book-publisher').textContent.toLowerCase();
                
                if (title.includes(searchTerm) || 
                    author.includes(searchTerm) || 
                    publisher.includes(searchTerm)) {
                    card.style.display = '';
                } else {
                    card.style.display = 'none';
                }
            });
        });
    }

    // 确认删除对话框
    const deleteButtons = document.querySelectorAll('.delete-btn');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            if (!confirm('确定要删除这本书吗？')) {
                e.preventDefault();
            }
        });
    });
});

// 表单验证
function validateBookForm() {
    const title = document.getElementById('title').value;
    const author = document.getElementById('author').value;
    const publisher = document.getElementById('publisher').value;
    const content = document.getElementById('content').value;
    
    if (!title || !author || !publisher || !content) {
        alert('请填写所有必填字段');
        return false;
    }
    
    return true;
}

// 图片预览功能
function previewImage(input) {
    const preview = document.getElementById('coverPreview');
    if (input.files && input.files[0]) {
        const reader = new FileReader();
        
        reader.onload = function(e) {
            preview.src = e.target.result;
            preview.style.display = 'block';
        }
        
        reader.readAsDataURL(input.files[0]);
    }
}

// 响应式导航菜单
function toggleMenu() {
    const menu = document.querySelector('.nav-menu');
    menu.classList.toggle('active');
}

// 页面加载动画
window.addEventListener('load', function() {
    const loader = document.querySelector('.loader');
    if (loader) {
        loader.style.display = 'none';
    }
});