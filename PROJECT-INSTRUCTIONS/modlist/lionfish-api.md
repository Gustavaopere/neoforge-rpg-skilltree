# Lionfish API

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81738622c0690902582a
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Lionfish API
- **Arquivo JAR:** `lionfishapi-3.1.jar`
- **Versão 1.21.1:** 3.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca
- **Função:** API/biblioteca leve de animação da linha L_Ender's Mods, usada como infraestrutura por mods consumidores em vez de adicionar gameplay autônomo.
- **Dependências:** A publicação da Lionfish API 3.1 não lista dependências obrigatórias. No pack, L_Ender's Cataclysm 3.33 está presente e sua página oficial lista Lionfish API como dependência obrigatória.
- **Sobreposição:** Infraestrutura compartilhada, não provider de mobs/bosses. Cataclysm permanece authority de entidades, AI, combate e rewards; Lionfish API fornece suporte de animação.
- **Compatibilidade/Riscos:** Source público localizado está desatualizado para a build 3.1. Riscos: API/ABI drift com consumidores, falhas de animação/model, client/server class leakage, duplicate bundled library e removal fan-out em Cataclysm.
- **Observações:** Release exata 3.1 confirmada em 30/06/2026. Cataclysm 3.33 exige Lionfish API no pack. Source público exato de 3.1 não foi localizado; classes/métodos internos não foram inventados.
- **Procedência:** modlist.txt física atual + release oficial Lionfish API 3.1 NeoForge 1.21.1 + relação oficial de dependências de L_Ender's Cataclysm 3.33 + repositório público lender544/Lionfish-API, tratado apenas como predecessor por não representar claramente 3.1.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/lionfish-api
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — release 3.1 reconciliada; papel de animation API, consumer Cataclysm 3.33, client/server boundary, source gap, riscos de ABI/lifecycle e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

> 🦁 **ESCOPO CANÔNICO.** Runtime físico: `lionfishapi-3.1.jar`, mod id `lionfishapi`, versão `3.1`. Lionfish API é uma biblioteca/API leve de animação da linha L_Ender's Mods; não adiciona um sistema de gameplay autônomo.

## 1. Identidade e autoridade de versão
A modlist física e a publicação oficial convergem em `lionfishapi-3.1.jar`, release para NeoForge 1.21.1 publicada em 30/06/2026. A página oficial descreve o projeto como **Very Light Animation API** / nova animation API dos mods L_Ender's. Essa release é a authority para versão e plataforma.

## 2. Source gap
O repositório público `lender544/Lionfish-API` foi localizado, mas seu histórico público não representa de forma inequívoca a build 3.1 distribuída. Portanto ele é tratado como predecessor/linha de desenvolvimento, não como source pin exato. Nenhuma classe, método ou assinatura de API específica da 3.1 é afirmada a partir dele.

## 3. Papel no modpack
A biblioteca fornece infraestrutura de animação para mods consumidores. Ela não é authority de spawn, AI, dano, loot, estruturas ou progressão dos mobs que a utilizam. Esses comportamentos permanecem nos consumers.

## 4. Consumer concreto: L_Ender's Cataclysm
O pack contém `L_Ender's Cataclysm 1.21.1-3.33.jar`. A página oficial de dependências de Cataclysm lista **Lionfish API como Required Dependency**, ao lado de Curios API. Portanto a relação de consumo é concreta, não inferida apenas por autoria ou nome.

## 5. Boundary de ownership
Lionfish API pode participar da definição/reprodução de animações, mas Cataclysm continua provider de entidades, bosses, AI, ataques, damage windows, loot e worldgen. Integrações próprias não devem creditar Lionfish API por um ataque simplesmente porque a animação correspondente passa por sua infraestrutura.

## 6. Client / server boundary
Render/model/pose e reprodução visual são majoritariamente client-facing. Entretanto a biblioteca é publicada como **Client & Server**, então consumidores podem carregar contratos comuns em ambos os lados. Dedicated server precisa inicializar sem puxar renderers/client classes para caminhos de lógica comum.

## 7. Atualização e ABI
Como biblioteca, atualização isolada apresenta risco de API/ABI drift: consumer compilado contra outra revisão pode falhar em bootstrap, classloading ou durante a reprodução de uma animação. Atualizar Lionfish deve ser tratado junto com regressão de Cataclysm e qualquer outro consumer real do pack.

## 8. Lifecycle
Validar bootstrap de registry/event hooks, spawn de entidades consumidoras, início/interrupção de animações, death/despawn, chunk unload e relog. Uma animação interrompida por remoção de entidade não deve manter referências/ticks client-side indefinidamente.

## 9. Multiplayer e authority
A pose/animação vista pelo cliente não deve decidir se um hit ocorreu. Dano, invulnerabilidade, cooldown e kill credit pertencem ao servidor/consumer. Em multiplayer, clientes distintos devem observar a animação compatível com o state servidor sem que divergência visual gere um segundo efeito causal.

## 10. Riscos técnicos
1. **API/ABI drift** entre Lionfish 3.1 e consumers.
2. **Source gap** dificulta auditoria estática exata da build.
3. **Client class leakage** em dedicated server.
4. **Animation/state desync** entre clientes e entidade server-side.
5. **Duplicate library copy** se consumer empacotar revisão incompatível.
6. **Removal fan-out:** retirar Lionfish impede Cataclysm 3.33 de satisfazer dependência obrigatória.
7. **Visual/causal confusion:** usar animation event como prova de dano/kill em integração externa.

## 11. Matriz de testes
- [ ] Dedicated server inicia com Lionfish API 3.1 + Cataclysm 3.33.
- [ ] Cataclysm satisfaz a dependência sem missing class/method.
- [ ] Entidades/bosses consumidores reproduzem animações sem missing model/pose.
- [ ] Spawn→attack→death→despawn não deixa animações/ticks órfãos.
- [ ] Chunk unload/reload não duplica controllers ou efeitos visuais.
- [ ] Dois clientes observam state de animação coerente em combate.
- [ ] Hit/damage ocorre uma única vez no servidor independentemente de frames visuais.
- [ ] Atualização futura da API só ocorre após regressão dos consumers.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 12. Evidências e limites
Foram usados a modlist física, a release oficial Lionfish API 3.1 e a relação oficial de dependências de Cataclysm 3.33. O repositório público localizado não foi tratado como source exato da 3.1. Registry/API internals não documentados oficialmente permaneceram deliberadamente fora da ficha.
