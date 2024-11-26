  INSERT INTO role (name)
  VALUES ('USER');
  INSERT INTO role (name)
  VALUES ('OWNER');
  INSERT INTO role (name)
  VALUES ('ADMIN');

DROP TABLE category CASCADE;
DROP TABLE category_id_seq CASCADE;
DROP TABLE customer CASCADE;
DROP TABLE customer_id_seq CASCADE;
DROP TABLE dish CASCADE;
DROP TABLE dish_id_seq CASCADE;
DROP TABLE dish_order CASCADE;
DROP TABLE dish_tags CASCADE;
DROP TABLE ingredient CASCADE;
DROP TABLE ingredient_id_seq CASCADE;
DROP TABLE location CASCADE;
DROP TABLE location_id_seq CASCADE;
DROP TABLE order_entity CASCADE;
DROP TABLE order_entity_dishes CASCADE;
DROP TABLE order_entity_id_seq CASCADE;
DROP TABLE restaurant CASCADE;
DROP TABLE restaurant_id_seq CASCADE;
DROP TABLE role CASCADE;
DROP TABLE role_id_seq CASCADE;
DROP TABLE tag CASCADE;
DROP TABLE tag_id_seq CASCADE;

--INSERT INTO RESTAURANT (id, verified, logo_url, name, phone, location_id, customer_id)
--VALUES (1, true, 'logo', 'Restaurant name', '088888888', null, 2);