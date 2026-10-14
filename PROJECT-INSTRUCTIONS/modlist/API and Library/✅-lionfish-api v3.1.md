# Lionfish API

## Propriedades do registro

- **Mod:** Lionfish API
- **Arquivo JAR:** lionfishapi-3.1.jar
- **Versão 1.21.1:** 3.1
- **Categoria:** Biblioteca
- **Tipo de conteúdo:** Mod
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/lionfish-api
- **Função:** API/biblioteca leve de animação da linha L_Ender's Mods, usada como infraestrutura por mods consumidores em vez de adicionar gameplay autônomo.
- **Dependências:** A publicação da Lionfish API 3.1 não lista dependências obrigatórias. No pack, L_Ender's Cataclysm 3.33 está presente e sua página oficial lista Lionfish API como dependência obrigatória.
- **Compatibilidade/Riscos:** Source público localizado está desatualizado para a build 3.1. Riscos: API/ABI drift com consumidores, falhas de animação/model, client/server class leakage, duplicate bundled library e removal fan-out em Cataclysm.
- **Sobreposição:** Infraestrutura compartilhada, não provider de mobs/bosses. Cataclysm permanece authority de entidades, AI, combate e rewards; Lionfish API fornece suporte de animação.
- **Observações:** Release exata 3.1 confirmada em 30/06/2026. Cataclysm 3.33 exige Lionfish API no pack. Source público exato de 3.1 não foi localizado; classes/métodos internos não foram inventados.
- **Procedência:** Snapshot físico de 25/09/2026 + CurseForge oficial **File 8345326** (Lionfish API 3.1 NeoForge 1.21.1, 30/06/2026) e changelog `More Optimization` + dependência obrigatória de Cataclysm 3.33 + source público master `ae02f676...` que indica linha antiga de MC 1.20.1, sem equivalência certificada ao binário 3.1.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 14/10/2026 — último snapshot físico 25/09/2026 mantém **Lionfish API 3.1**. A publicação CurseForge **File 8345326 de 30/06/2026** segue a release NeoForge 1.21.1 mais recente; changelog `More Optimization` sem métricas/assinaturas de API. Source público antigo não representa versão 3.1; dependente Cataclysm 3.33 e QA de ABI/animação permanecem pendentes.
- **Data da última decisão:** 2026-08-26

> **Última autoridade física verificada — snapshot de 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #374: JAR `lionfishapi-3.1.jar`, mod id `lionfishapi`, runtime `3.1`, SHA-1 `353ca00ed118eb67cf11085abbbd699884722326`.

> **ESCOPO CANÔNICO.** Snapshot físico de 25/09/2026: `lionfishapi-3.1.jar`, mod ID `lionfishapi`, versão 3.1. Biblioteca de animação, não provider de gameplay; Cataclysm 3.33 é consumidor obrigatório. A release oficial 3.1 permanece a mais recente publicada para NeoForge 1.21.1. O binário em uso não foi reaberto em outubro.
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

## 13. Revalidação da versão 3.1 e contrato de dependência de Cataclysm — 14/10/2026

### 13.1. Artefato e compatibilidade version-specific

**Autoridade física histórica:** snapshot #374 de **25/09/2026**: `lionfishapi-3.1.jar`, mod ID `lionfishapi`, versão interna `3.1`, SHA-1 `353ca00ed118eb67cf11085abbbd699884722326`. Não foi gerada nova verificação por hash/metadata do JAR em outubro.

**Distribuição primária:** [CurseForge Lionfish API **File ID 8345326**](https://www.curseforge.com/minecraft/mc-mods/lionfish-api/files/8345326), publicada em **30/06/2026**, **Minecraft 1.21.1 NeoForge**, release (não Beta), `lionfishapi-3.1.jar`, aproximadamente **89,4 KB**, ambiente **Client & Server**. O [índice oficial filtrado por 1.21.1](https://www.curseforge.com/minecraft/mc-mods/lionfish-api/files/all?page=1&pageSize=20&version=1.21.1) mostra 3.1 como última versão da linha, sem revisão posterior identificada em 14/10/2026. A coincidência de nome e número **não substitui comparação de hash** do download oficial com o JAR atual.

**Changelog da 3.1:** o autor publica apenas **“More Optimization”**. Isso **não confirma** ganho de FPS, menor tick time, redesenho de API, mudança de classe, fix de memory leak ou compatibilidade binária com toda revisão de Cataclysm. Não atribuir métricas nem breaking changes não relatadas.

**Licença oficial:** a [página do projeto](https://www.curseforge.com/minecraft/mc-mods/lionfish-api) declara **GNU LGPLv3**; não inventar direitos/licenciamento específico de assets ou de um JAR modificado não inspecionado.

### 13.2. Origem do source gap e consumer comprovado

O link Source do CurseForge conduz a [`lender544/Lionfish-API`](https://github.com/lender544/Lionfish-API). A branch `master` pinada no [commit `ae02f676b2479264cf453cda1d35a647cb35f326`](https://github.com/lender544/Lionfish-API/tree/ae02f676b2479264cf453cda1d35a647cb35f326) possui [`gradle.properties`](https://github.com/lender544/Lionfish-API/blob/ae02f676b2479264cf453cda1d35a647cb35f326/gradle.properties) ainda com `jei_mc_version=1.20.1` e README genérico de template. Assim, **não tratar essa revisão como source exato da distribuição 3.1 para NeoForge 1.21.1**; métodos, packages, APIs de animation controller, codecs e assinaturas da release 3.1 permanecem **não certificados** por source.

**Dependente confirmado:** [L_Ender’s Cataclysm 3.33](https://www.curseforge.com/minecraft/mc-mods/lendercataclysm/relations/dependencies) declara Lionfish API e Curios como **required**. O snapshot contém **Cataclysm #368**, versão de distribuição `3.33`, e **Lionfish #374 3.1**. O [source 1.21 de Cataclysm pinado no commit `10e21131b8eb2ce92be416409f91925e8c5decf0`](https://github.com/lender544/new1.20.1/tree/10e21131b8eb2ce92be416409f91925e8c5decf0) declara `lf_version_range=3.1` como configuração de build. Isso é coerência nominal, **não** prova de sucesso de classloading/ABI ou confirmação de metadata do JAR físico.

Lionfish é **provider de infra de animação**, não de mobs, hitbox, dano, boss phases, projectile collision ou loot. Cataclysm permanece dono de lógica de combate e state do servidor; a animação pode representar esses eventos, mas uma perk de RPG Skill Tree não deve inferir dano/crítico/kill/cooldown por frame de pose, asset ou controller visual.

### 13.3. QA específico e decisão

- [ ] Inspecionar o JAR `lionfishapi-3.1.jar` em uso: SHA-1/SHA-256, `META-INF/neoforge.mods.toml`, mod ID, dependências, Mixin configs, packages e classes; confrontar com File 8345326. Não contar libraries aninhadas como novos mods top-level.
- [ ] Confirmar a metadata exata do Cataclysm #368 3.33 e dependências obrigatórias Curios #211 e Lionfish #374; não substituir Lionfish enquanto houver dependente obrigatório.
- [ ] Cold boot de cliente **e** servidor dedicado com Cataclysm 3.33 e Lionfish 3.1, inspecionar linkage/`NoClassDefFoundError`/`NoSuchMethodError` e invocação de classes client-only em ambiente server.
- [ ] Testar animações de bosses/entidades Cataclysm em spawn, ataques, transições de fase, morte/despawn, reload/relog, dimension change, chunk unload e dois clientes multiplayer. Correlacionar VFX observados com dano/evento autoritativo separado no servidor.
- [ ] Em QA de performance, registrar frame time, alocações e latência/ticks antes de afirmar qualquer melhoria pelo changelog `More Optimization`. Sem benchmarks prévios e equivalência de ambiente, **não anunciar ganhos**.
- [ ] Auditar JARs/metadata de qualquer segundo consumer antes de alegar dependência adicional; não inventar conflitos com frameworks de animação de outros mods por sobreposição superficial.

**Decisão:** manter o item **3.1** no inventário conforme snapshot e preservar a propriedade histórica **Sem decisão**. Não existe update NeoForge 1.21.1 mais recente localizado que exija promoção. **Nenhuma modlist, JAR, config, mundo, script, datapack, shader ou resource pack alterados.** Todos os testes runtime permanecem **pendentes**.
