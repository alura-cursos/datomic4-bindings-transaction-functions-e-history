(ns ecommerce.aula5
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

(def ola
  (d/function '{:lang   :clojure
                :params [nome]
                :code   (str "Olá " nome)}))

(pprint (ola "Guilherme"))

(def incrementa-visualizacao
  #db/fn {
          :lang   :clojure
          :params [db produto-id]
          :code
                  (let [visualizacoes (d/q '[:find ?visualizacoes .
                                             :in $ ?id
                                             :where [?produto :produto/id ?id]
                                             [?produto :produto/visualizacoes ?visualizacoes]]
                                           db produto-id)
                        atual (or visualizacoes 0)
                        total-novo (inc atual)]
                    [{:produto/id            produto-id
                      :produto/visualizacoes total-novo}])})

; instalar a funcao
(pprint @(d/transact
          conn
          [{:db/doc   "Incrementa o atributo :produto/visualizacoes de uma entidade"
            :db/ident :incrementa-visualizacao
            :db/fn    incrementa-visualizacao}]))


(dotimes [n 10] (db/visualizacao! conn (:produto/id primeiro)))
(pprint (db/um-produto (d/db conn) (:produto/id primeiro)))


(dotimes [n 10] (db/visualizacao! conn (:produto/id primeiro)))
(pprint (db/um-produto (d/db conn) (:produto/id primeiro)))










