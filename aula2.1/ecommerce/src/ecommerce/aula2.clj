(ns ecommerce.aula2
  (:use clojure.pprint)
  (:require [datomic.api :as d]
            [ecommerce.db :as db]
            [ecommerce.model :as model]
            [schema.core :as s]))

(s/set-fn-validation! true)

(db/apaga-banco!)
(def conn (db/abre-conexao!))
(db/cria-schema! conn)
(db/cria-dados-de-exemplo conn)

(def produtos (db/todos-os-produtos (d/db conn)))
(def primeiro (first produtos))
(pprint primeiro)

(pprint @(db/atualiza-preco! conn (:produto/id primeiro) 20M 30M))
(pprint @(db/atualiza-preco! conn (:produto/id primeiro) 30M 35M))
;(pprint @(db/atualiza-preco! conn (:produto/id primeiro) 30M 45M))




