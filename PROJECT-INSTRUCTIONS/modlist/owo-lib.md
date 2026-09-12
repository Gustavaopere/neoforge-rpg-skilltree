# oωo (owo-lib) — 0.12.15.5-beta.1+1.21

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3d569db9f0db819ca4fbe2a45da27218  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-10

## Propriedades do registro

- **Mod:** oωo (owo-lib)
- **Arquivo JAR:** `owo-lib-neoforge-0.12.15.5-beta.1+1.21.jar`
- **Versão 1.21.1:** `0.12.15.5-beta.1+1.21`
- **Categoria:** Biblioteca
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:**
- **Função:** Library de infraestrutura para UI declarativa, config annotation-driven/sync, networking com handshake e serialização Endec, além de utilidades para mods consumidores.
- **Dependências:** NeoForge 1.21.1. O JAR físico embarca Endec/Jankson/Forgified Fabric API Base components em `/META-INF/jars/`; não promovê-los a top-level. Consumers reais precisam ser mapeados antes de remoção.
- **Compatibilidade/Riscos:** Build Beta 0.12.15.5-beta.1+1.21. Riscos: API drift, handshake/config-sync mismatch, client classloading, jar-in-jar ambiguity e regression de DerivedComponentMap. Beta é maturidade, não ausência de evidência técnica.
- **Sobreposição:** Biblioteca load-bearing potencial; não substitui gameplay. Top-level Forgified Fabric API coexistindo com fabric-api-base interno não deve ser tratado como conflito sem observar resolução do loader.
- **Observações:** JAR físico `owo-lib-neoforge-0.12.15.5-beta.1+1.21.jar`, mod id `owo`, runtime 0.12.15.5-beta.1+1.21. Changelog tenta corrigir igualdade de DerivedComponentMap; build é Beta para NeoForge 1.21.1.
- **Procedência:** modlist(4).txt — fonte física canônica atual, 595 mods top-level; JAR `owo-lib-neoforge-0.12.15.5-beta.1+1.21.jar`, mod id `owo`, runtime `0.12.15.5-beta.1+1.21`, hash físico `48dda11a6710591cf162bdbedf982ea21dd1f2ed`. Modrinth oficial, Wisp Forest Docs e hierarquia jar-in-jar física permanecem como evidência complementar.
- **Histórico da decisão:**
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — placeholder de reconciliação substituído por dossiê técnico; owo-ui/config/networking/Endec, jar-in-jar físico, Beta boundary e regression gates catalogados.
- **Data da última decisão:** não registrada

## Dossiê operacional — padrão Alex's Mobs

> 🧱 **ESCOPO CANÔNICO.** Runtime físico: `owo-lib-neoforge-0.12.15.5-beta.1+1.21.jar`, mod id `owo`, versão `0.12.15.5-beta.1+1.21`, NeoForge 1.21.1. oωo é uma library Client & Server de UI, config, networking/serialization e utilidades; a build física é **Beta**, mas possui publicação e documentação oficiais suficientes para catalogação técnica.

## 1. owo-ui
`owo-ui` é framework declarativo de interfaces baseado em árvore de componentes. Suporta layouts dinâmicos, components reutilizáveis, animações e modelos XML além de construção por código. Essa superfície é client-facing; estado de gameplay mostrado por uma UI continua pertencendo ao provider/server.

## 2. owo-config
O sistema de config é annotation-driven e gera wrapper/config screen. A documentação registra constraints, callbacks, integração de screen e sincronização opcional server↔client.

Sync modes incluem nenhuma sync, envio client→server e override server→client. Opções que exigem igualdade/restart podem impedir conexão quando incompatíveis; isso torna config versioning um risco real para servers.

## 3. Networking e Endec
`OwoNetChannel` fornece channel abstraction e usa **Endec** para serialização. O handshake verifica que client e server possuem layout de channel compatível, reduzindo risco de payload decodificado com schema divergente.

Integrações externas devem respeitar ownership do protocolo do consumer; não reutilizar packet IDs/layouts internos sem API pública.

## 4. Hierarquia jar-in-jar física
A inspeção da modlist física mostra dentro do JAR oωo:
- `endec-0.1.8.1.jar`;
- `gson-0.1.5.1.jar`;
- `jankson-0.1.5.1.jar`;
- `netty-0.1.4.1.jar`;
- `jankson-1.2.2.jar`;
- `fabric-api-base-0.4.42+d1308dedd1.jar`.

Esses artefatos são **internos ao parent oωo** e não são entradas top-level do catálogo. O pack também possui Forgified Fabric API top-level; isso não é automaticamente conflito — loader/dependency resolution precisa ser observado no runtime.

## 5. Build exata e changelog
A publicação oficial 0.12.15.5-beta.1+1.21 é Beta para NeoForge 1.21.1. O changelog registra tentativa de corrigir problemas de igualdade com `DerivedComponentMap`, resolvendo incompatibilidades com mods como Pastel. Esse é regression gate da build, não prova de bug local atual.

## 6. Authority e consumers
oωo não fornece gameplay principal por si. Consumers usam UI/config/network/serialization. Remoção só é segura após mapear dependentes top-level e jar-in-jar resolution; uma library aparentemente “sem conteúdo” pode ser load-bearing.

## 7. Lifecycle
Validar cold boot, dedicated server, client handshake, config load/save/sync, reconnect, resource reload de XML UI e update de consumer. Config/client mismatch e packet schema mismatch precisam falhar de forma controlada, nunca corromper state.

## 8. Riscos
1. **API drift:** consumer compilado contra outra versão de owo.
2. **Handshake mismatch:** packet layouts divergem client/server.
3. **Config sync:** opção client/server conflitante causa comportamento diferente ou bloqueio de conexão.
4. **Client classloading:** UI chamada em server path.
5. **Jar-in-jar ambiguity:** dependency interna confundida com top-level ou versão forçada manualmente.
6. **Beta regression:** build é Beta e exige smoke real do stack consumidor.
7. **Data-component equality:** regressão ligada a `DerivedComponentMap` reaparece.

## 9. Boundary para quests/perks
UI/config/network helpers nunca são events de Mastery por si. Qualquer progresso deve vir do state/evento do consumer que usa oωo, não da abertura de screen ou chegada de packet genérico.

## 10. Matriz de testes
- [ ] Dedicated server inicia com oωo 0.12.15.5-beta.1.
- [ ] Client com stack idêntico conclui handshake.
- [ ] Config synced converge server/client após reconnect.
- [ ] Config mismatch restart-sensitive falha de modo compreensível.
- [ ] owo-ui screens dos consumers abrem após resource reload.
- [ ] Packets de consumers não causam decode/classloading errors.
- [ ] Items/components relevantes não reproduzem equality regression da build.
- [ ] Jar-in-jar dependencies são resolvidas sem promover duplicatas top-level.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 11. Evidências e limite
Modrinth oficial confirma build Beta, NeoForge 1.21.1, Client & Server e changelog `DerivedComponentMap`. Wisp Forest Docs confirmam owo-ui, owo-config, config sync, networking e Endec. A modlist física confirma a hierarquia `/META-INF/jars/`. Consumers exatos ainda precisam de dependency graph completo antes de qualquer remoção.
