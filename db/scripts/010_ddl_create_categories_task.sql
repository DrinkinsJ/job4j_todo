CREATE TABLE categories_task (
id SERIAL PRIMARY KEY,
task_id int not null REFERENCES tasks(id),
categories_id int not null REFERENCES categories(id)
)