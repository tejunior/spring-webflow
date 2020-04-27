CREATE TABLE USER_LOGIN (
  id UUID NOT NULL,
  enabled boolean NOT NULL,
  name character varying(255),
  email character varying(255),
  created_at timestamptz NOT NULL DEFAULT now(),
  updated_at timestamptz NOT NULL DEFAULT now()
);

ALTER TABLE USER_LOGIN ADD CONSTRAINT pkey_user_id         PRIMARY KEY (id);
ALTER TABLE USER_LOGIN ADD CONSTRAINT uq_user_email        UNIQUE (email);
