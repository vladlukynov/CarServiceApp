USE CarService;

-- Таблица автомобилей, которые может обслуживать сервис
CREATE TABLE Cars (
	CarId INT IDENTITY(1, 1) PRIMARY KEY,
	Manufacturer VARCHAR(30) NOT NULL,
	CarModel VARCHAR(30) NOT NULL,
	ReleaseYear SMALLINT NOT NULL
		CHECK(ReleaseYear >= 1990 AND
			ReleaseYear <= YEAR(GETDATE())),
	IsActive BIT NOT NULL DEFAULT 1
);

-- Таблица запчастей
CREATE TABLE Details (
	DetailId INT IDENTITY(1, 1) PRIMARY KEY,
	DetailName VARCHAR(30) NOT NULL,
	Price MONEY NOT NULL CHECK(Price >= 0),
	Quantity INT NOT NULL CHECK(Quantity >= 0)
);

-- Таблица ролей
CREATE TABLE Roles (
	RoleId INT IDENTITY(1, 1) PRIMARY KEY,
	RoleName NVARCHAR(15) NOT NULL
);

-- Таблица услуг
CREATE TABLE Services (
	ServiceId INT IDENTITY(1, 1) PRIMARY KEY,
	ServiceName NVARCHAR(50) NOT NULL,
	Description NVARCHAR(150) NOT NULL,
	Price MONEY NOT NULL CHECK(Price >= 0),
	IsActive BIT NOT NULL DEFAULT 1
);

-- Таблица пользователей
CREATE TABLE Users (
	UserLogin VARCHAR(30) PRIMARY KEY,
	Pass VARCHAR(255) NOT NULL,
	Email VARCHAR(30) NOT NULL UNIQUE CHECK(Email LIKE '%@%.%'),
	PhoneNumber VARCHAR(15) NOT NULL UNIQUE CHECK(PhoneNumber LIKE '+7[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'),
	RoleId INT NOT NULL FOREIGN KEY REFERENCES Roles(RoleId),
	IsActive BIT NOT NULL DEFAULT 1
);

-- Таблица сотрудников
CREATE TABLE Employees (
	UserLogin VARCHAR(30) NOT NULL FOREIGN KEY REFERENCES Users(UserLogin),
	FirstName NVARCHAR(20) NOT NULL,
	LastName NVARCHAR(20) NOT NULL,
	MiddleName NVARCHAR(20) NOT NULL,
	Post NVARCHAR(20) NOT NULL,
	Salary MONEY NOT NULL CHECK(Salary >= 0),
	Birthday DATE NOT NULL CHECK(DATEDIFF(year, Birthday, GETDATE()) >= 18)
);

-- Таблица клиентов
CREATE TABLE Clients (
	UserLogin VARCHAR(30) NOT NULL FOREIGN KEY REFERENCES Users(UserLogin),
	FirstName NVARCHAR(20) NOT NULL,
	LastName NVARCHAR(20) NOT NULL,
	MiddleName NVARCHAR(20) NOT NULL,
	Birthday Date NOT NULL CHECK(DATEDIFF(year, Birthday, GETDATE()) >= 18)
);

-- Таблица заказов
CREATE TABLE Orders (
	OrderId INT IDENTITY(1, 1) PRIMARY KEY,
	CarId INT NOT NULL FOREIGN KEY REFERENCES Cars(CarId),
	CarNumber NVARCHAR(9) NOT NULL
		CHECK(CarNumber LIKE N'[А-Я][0-9][0-9][0-9][А-Я][А-Я][0-9][0-9][0-9]' OR
			CarNumber LIKE N'[А-Я][0-9][0-9][0-9][А-Я][А-Я][0-9][0-9]'),
	Status NVARCHAR(20) NOT NULL DEFAULT N'Создан',
	CreationDate DATE NOT NULL DEFAULT GETDATE(),
	StatusChangeDate Date CHECK(StatusChangeDate <= GETDATE()),
	EmployeeLogin VARCHAR(30) FOREIGN KEY REFERENCES Users(UserLogin),
	ClientLogin VARCHAR(30) NOT NULL FOREIGN KEY REFERENCES Users(UserLogin)
);

-- Таблица для связи заказа с используемыми деталями
CREATE TABLE OrdersDetails (
	OrderId INT NOT NULL FOREIGN KEY REFERENCES Orders(OrderId),
	DetailId INT NOT NULL FOREIGN KEY REFERENCES Details(DetailId),
	Quantity INT NOT NULL CHECK(Quantity >= 0)
);

-- Таблица для связи заказа с оказанными услугами
CREATE TABLE OrdersServices (
	OrderId INT NOT NULL FOREIGN KEY REFERENCES Orders(OrderId),
	ServiceId INT NOT NULL FOREIGN KEY REFERENCES Services(ServiceId),
	Quantity INT NOT NULL CHECK(Quantity >= 0)
);
