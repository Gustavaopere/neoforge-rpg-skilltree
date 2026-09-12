# CERBON's API

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d469db9f0db81bd9938fd6e7025c774
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** CERBON's API
- **Arquivo JAR:** `CerbonsAPI-NeoForge-1.21-1.3.0.jar`
- **Versão 1.21.1:** 1.3.0
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Dependência
- **Categoria:** Biblioteca
- **Função:** Biblioteca compartilhada do ecossistema CERBON, concentrando código e infraestrutura reutilizados pelos mods do autor.
- **Dependências:** NeoForge 1.21.1. Consumer físico e oficialmente obrigatório confirmado: Bosses of Mass Destruction 1.3.3. Cloth Config/GeckoLib são dependências do BOMD, não promovidas aqui a dependências diretas da API.
- **Sobreposição:** Biblioteca específica do ecossistema CERBON; não substituível por biblioteca genérica apenas por função semelhante.
- **Compatibilidade/Riscos:** Library load-bearing para consumers CERBON. Riscos: consumer/API drift, linkage errors, registro/menu em fase errada, S2C packet/side mismatch e atualização isolada sem exigência do consumer. O próprio upstream alerta para atualizar conforme necessidade do mod dependente.
- **Observações:** JAR físico `CerbonsAPI-NeoForge-1.21-1.3.0.jar`, mod id `cerbons_api`, runtime 1.3.0. Release oficial NeoForge 1.21/1.21.1 de 01/05/2025; 1.3.0 adiciona menu helpers, HistoricalData constructor, BlockRegistry/ItemRegistry e novos IPlatformHelper methods.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge oficial CERBON's API 1.3.0 + relação oficial de dependência do Bosses of Mass Destruction 1.3.3 e fontes já auditadas. Reconciliação final: JAR/runtime permanecem exatamente `CerbonsAPI-NeoForge-1.21-1.3.0.jar` / `1.3.0`; decisão `Dependência` permanece inalterada.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/cerbons-api
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — reconciliação final física #91: `CerbonsAPI-NeoForge-1.21-1.3.0.jar` / `1.3.0` conferidos contra a modlist atual; consumer BOMD 1.3.3, menu/registry/platform helpers, networking/lifecycle e decisão `Dependência` preservados.
- **Histórico da decisão:** 2026-09-07 — novo registro; classificado como Dependência por uso de Bosses of Mass Destruction.
- **Data da última decisão:** 2026-09-07

> 🧩 **ESCOPO CANÔNICO.** Runtime físico: `CerbonsAPI-NeoForge-1.21-1.3.0.jar`, mod id `cerbons_api`, versão `1.3.0`, NeoForge 1.21.1. CERBON's API é uma biblioteca multiloader do ecossistema CERBON; não adiciona uma linha de gameplay própria.

## 1. Papel e authority
A biblioteca concentra helpers de plataforma, registries, menus e dados reutilizados por mods consumidores. O consumer físico confirmado neste pack é **Bosses of Mass Destruction 1.3.3**, que publica CERBON's API como dependência obrigatória.
A API não é authority dos bosses, loot, combate ou worldgen do BOMD; esses sistemas permanecem no consumer.

## 2. Superfícies exatas da 1.3.0
O changelog oficial 1.3.0 adiciona:
- helper de menu type para registrar e abrir menus em todos os loaders suportados;
- novo construtor de `HistoricalData` sem valor default obrigatório;
- classes `BlockRegistry` e `ItemRegistry` para registro de conteúdo;
- novos métodos em `IPlatformHelper`.
Esses pontos são evidência da build exata e delimitam o que integrações podem esperar da API sem inventar gameplay.

## 3. Abstração multiloader
Desde a linha 1.1.0 o projeto foi movido para arquitetura multiloader. Isso significa que consumidores compartilham contratos comuns, enquanto detalhes Forge/Fabric/NeoForge ficam atrás de helpers de plataforma.
Código próprio não deve chamar internals específicos do loader quando a API pública do consumer já oferece um contrato estável.

## 4. Networking e histórico de regressão
A 1.1.1 corrigiu packets NeoForge Server→Client. Embora seja histórico anterior à 1.3.0, confirma networking como superfície real da biblioteca e justifica teste de menus/sync em dedicated server após updates.

## 5. Registry e persistência
Helpers de Block/Item registry reduzem boilerplate, mas registry IDs continuam pertencendo ao mod consumidor. Renomear/remover conteúdo de um consumer não é responsabilidade de CERBON's API e exige migration no próprio provider.
`HistoricalData` é infraestrutura de dados; esta auditoria não presume seu schema interno nem quais consumidores o usam sem source/runtime específico.

## 6. Runtime físico do pack
- CERBON's API: 1.3.0.
- Bosses of Mass Destruction: 1.3.3.
- BOMD também possui suas próprias dependências, como Cloth Config e GeckoLib; isso **não** as transforma em dependências diretas da CERBON's API.
A página oficial da API alerta para não atualizar a biblioteca arbitrariamente se o mod dependente não exigir a nova linha.

## 7. Lifecycle e sides
Validar cold boot, registry setup, abertura de menus, packet sync, reconnect e dedicated server. Helpers client-facing não podem ser carregados em caminhos common/server de forma indevida.

## 8. Riscos
1. Consumer/API drift entre BOMD e CERBON's API.
2. Linkage error por método/helper alterado.
3. Menu registration em fase errada.
4. Packet S2C com schema/side incompatível.
5. Remoção da library enquanto consumer obrigatório permanece.
6. Atualização da API por “ser mais nova” sem necessidade do consumer.

## 9. Matriz de testes
- [ ] Dedicated server inicia com CERBON's API 1.3.0 + BOMD 1.3.3.
- [ ] BOMD registra conteúdo sem missing classes/registries.
- [ ] Menus do consumer abrem e sincronizam corretamente.
- [ ] Reconnect não gera packet decode/registration errors.
- [ ] Client-only helpers não vazam para server path.
- [ ] Remoção controlada da API falha como dependência, confirmando o vínculo load-bearing.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 10. Evidências e limite
CurseForge oficial confirma a Release NeoForge 1.21/1.21.1 1.3.0 e o changelog acima; a página de dependências do BOMD confirma CERBON's API como required dependency. Não foi feita decompilação do JAR nesta etapa; métodos não documentados permanecem fora do contrato desta ficha.
